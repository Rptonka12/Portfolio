import arcpy
from arcpy import env
env.workspace = "Z:\AdvancedGIS\Assignment 7"
env.overwriteOutput=True

sr = arcpy.SpatialReference(4326)
#Creating line feature layer
outputFolder = "Z:\AdvancedGIS\Assignment 7"
outputName = "HurricaneTracks.shp"
arcpy.CreateFeatureclass_management(outputFolder, outputName, "POLYLINE")
print arcpy.GetMessages()

#3b: adding attribute fields
Name = "NAME"
NameType = "TEXT"
arcpy.AddField_management(outputName, Name, NameType, "", "", 15, "", "", "", "")
print arcpy.GetMessages()

Num = "TrackCount"
NumType = "INTEGER"
arcpy.AddField_management(outputName, Num, NumType)
print arcpy.GetMessages()

#3c: reading information from file
f = open(r"Z:\AdvancedGIS\Assignment 7\5Hurricanes.txt", "r")
cursor = arcpy.da.InsertCursor(outputName, ["SHAPE@"])
lines = f.readlines()
i=0
for line in lines:
    element = line.split(",")
    if (line.startswith("AL")):
        hdata = arcpy.Array();
        index = 0
        wname = element[1]
        while (wname[index] == " "):
            index = index + 1
        name = wname[index:len(wname)]
        print name
        tracks = float(element[2])
        points = 0
        i += 1
        lat = 0
        long = 0
    else:
        if (element[5].endswith("E")):
            coord = element[5]
            long = float(coord[0:len(coord)-1])
        if (element[5].endswith("W")):
            coord = element[5]
            long = float(coord[0:len(coord)-1])
            long = long * -1
        if (element[4].endswith("N")):
            coord = element[4]
            lat = float(coord[0:len(coord)-1])
        if (element[4].endswith("S")):
            coord = element[4]
            lat = float(coord[0:len(coord)-1])
            lat = lat * -1
        point = arcpy.Point(long, lat)
        hdata.append(point)
        points += 1
    if (tracks==hdata.count):
        hline = arcpy.Polyline(hdata)
        cursor.insertRow([hline, name, tracks])

print "Number of hurricanes detected: ", str(i)
f.close()