# Workspace
import arcpy.sa
from arcpy import env
env.workspace = "Z:\AdvancedGIS\FinalProject\Data1"
env.overwriteOutput = True

# Original Variables
hc_cities = "2010_Census_Cities.shp"
hc_parcels = "hennepin_county_metrogis_parcels.shp"
hc_schools = "school_program_locations.shp"
hc_streets = "Hennepin_County_Street_Centerlines.shp"

# Variables to be Calculated
Cities = "Cities.shp"
Parcels = "Parcels.shp"
Centroids = "Centroids.shp"
Schools = "Schools.shp"
WalkableParcels = "WalkableParcels.shp"

# 1. Clip Polygon Shapefiles to the Desired Service Area
arcpy.Select_analysis(hc_cities, Cities, '"NAME10" = \'Long Lake\' OR "NAME10" = \'Medicine Lake\' OR "NAME10" = \'Medina\' OR "NAME10" = \'Minnetonka Beach\' OR "NAME10" = \'Orono\' OR "NAME10" = \'Plymouth\' OR "NAME10" = \'Wayzata\'')
arcpy.Clip_analysis(hc_parcels, Cities, Parcels)
arcpy.Clip_analysis(hc_schools, Cities, Schools)

# 2. Calculate Parcel Centroid
# 2a. create new point feature class
outputFolder = env.workspace
arcpy.CreateFeatureclass_management(outputFolder, Centroids, "POINT")
print arcpy.GetMessages()

# 2b. calculate point geometry based on parcel centroids
cursor = arcpy.da.SearchCursor(Parcels, ["SHAPE@TRUECENTROID"])
cursor2 = arcpy.da.InsertCursor(Centroids, ["SHAPE@"])
pointdata = arcpy.Array()
for row in cursor:
    point = arcpy.Point(row[0][0],row[0][1])
    cursor2.insertRow([point])
del row
del cursor

# 3. Simplify Parcel Attributes
# 3a. create new field to store simplified parcel use types
LOT_TYPE = "LOT_TYPE"
lot_type_type = "TEXT"
arcpy.AddField_management(Parcels, LOT_TYPE, lot_type_type)
print arcpy.GetMessages()

# 3b. calculate new field based on USE1_DESC field
cursor = arcpy.da.UpdateCursor(Parcels, ["USE1_DESC", LOT_TYPE])
for row in cursor:
    if row[0].__contains__("Residential") or row[0] == "Condominium" or row[0] == "Cooperative" or row[0] == "Townhouse" or row[0] == "Double Bungalow" or row[0] == "Residential Lakeshore" \
            or row[0].__contains__("Apartment") or row[0].__contains__("Blind") or row[0].__contains__("Disabled") or row[0].__contains__("Housing") or row[0] == "Mobile Home Park"\
            or row[0] == "Nursing Home" or row[0].__contains__("Resd''l") or row[0] == "Triplex":
        row[1] = "Residential"
    elif row[0] == "Commercial":
        row[1] = "Commercial"
    elif row[0].__contains__("Agricultur") or row[0].__contains__("Farm"):
        row[1] = "Agriculture"
    elif row[0].__contains__("Vacant Land"):
        row[1] = "Vacant Land"
    elif row[0] == "Industrial" or row[0] == "Railroad" or row[0] == "Utility":
        row[1] = row[0]
    else:
        row[1] = "Other"
    cursor.updateRow(row)
del row
del cursor

# 4. Create Network Dataset
#    this was done in ArcMap using the parcel layer based on the hc_streets shapefile

# 5. Calculate Service Area
result = arcpy.CheckExtension("network")
if result=="Available":
    arcpy.CheckOutExtension("network")

    # Script Arguments
    Select_Layer_Type = arcpy.GetParameterAsText(0)
    if Select_Layer_Type == '#' or not Select_Layer_Type:
        Select_Layer_Type = "Facilities"  # provide a default value if unspecified

    Destinations_Layer = arcpy.GetParameterAsText(1)
    if Destinations_Layer == '#' or not Destinations_Layer:
        Destinations_Layer = "Z:\\AdvancedGIS\\FinalProject\\Data1\\Schools.shp"  # provide a default value if unspecified

    Default_Break_Values = arcpy.GetParameterAsText(2)
    if Default_Break_Values == '#' or not Default_Break_Values:
        Default_Break_Values = "500 1000"  # to produce 2 "buffers" of walkability, representing 5 minute and 10 minute walking distances

    # Local variables:
    StreetCenterlines_NetworkDataset_nd = "Z:\\AdvancedGIS\\FinalProject\\Data1\\StreetCenterlines_NetworkDataset.nd"
    Service_Area = "ServiceArea"
    Service_Area_with_Destinations = Service_Area
    Solved_Service_Area = Service_Area_with_Destinations
    Solve_Succeeded = "true"
    Parcels_shp = "Z:\\AdvancedGIS\\FinalProject\\Data1\\Parcels.shp"
    SAPolygons = "Z:\\AdvancedGIS\\FinalProject\\Data1\\SAPolygons.shp"
    AllWalkableParcels_shp = "Z:\\AdvancedGIS\\FinalProject\\Data1\\AllWalkableParcels.shp"
    WalkableParcels_shp = "Z:\\AdvancedGIS\\FinalProject\\Data1\\WalkableParcels.shp"

    # 5a: Make Service Area Layer
    SALayer = arcpy.MakeServiceAreaLayer_na(StreetCenterlines_NetworkDataset_nd, "ServiceArea", "Length", "TRAVEL_FROM",
                                  Default_Break_Values, "SIMPLE_POLYS", "NO_MERGE", "RINGS", "NO_LINES", "OVERLAP",
                                  "NO_SPLIT", "", "", "ALLOW_UTURNS", "Oneway", "TRIM_POLYS", "100 Meters",
                                  "NO_LINES_SOURCE_FIELDS", "NO_HIERARCHY", "")

    # 5b: Save Service Area Polygons to Disk
    SALayer = SALayer.getOutput(0)
    subLayerNames = arcpy.na.GetNAClassNames(SALayer)
    polygonLayerName = subLayerNames["SAPolygons"]
    PolygonsSubLayer = arcpy.mapping.ListLayers(SALayer, polygonLayerName)[0]

    # 5c: Add Schools as Destinations in Network Dataset
    arcpy.AddLocations_na(Service_Area, Select_Layer_Type, Destinations_Layer, "Name Address #", "5000 Meters", "",
                          "Hennepin_County_Street_Centerlines SHAPE;StreetCenterlines_NetworkDataset_Junctions NONE",
                          "MATCH_TO_CLOSEST", "APPEND", "NO_SNAP", "5 Meters", "INCLUDE",
                          "Hennepin_County_Street_Centerlines #;StreetCenterlines_NetworkDataset_Junctions #")

    # 5d: Solve Network Dataset
    arcpy.Solve_na(Service_Area_with_Destinations, "SKIP", "TERMINATE", "", "")

    # 5e: Create Service Area Polygons Shapefile
    arcpy.CopyFeatures_management(PolygonsSubLayer, "SAPolygons")

    # 5f: Create Shapefile of Walkable Parcels
    arcpy.SpatialJoin_analysis(Parcels_shp, SAPolygons, AllWalkableParcels_shp, "", "KEEP_COMMON", "", "", "", "")

    # 5g: Select Only Residential Parcels from Walkable Parcels Shapefile
    arcpy.Select_analysis(AllWalkableParcels_shp, WalkableParcels_shp, "\"LOT_TYPE\" = 'Residential'")

    arcpy.CheckInExtension("network")
else:
    print "Extension not available"