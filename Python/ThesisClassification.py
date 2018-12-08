# Workspace
import arcpy
from arcpy import env
env.workspace = "Z:\Thesis"
env.overwriteOutput = True

# Variables
Clients = "Z:\Thesis\Client Data\\1MileClients.shp"
Groceries = "Z:\Thesis\Data1\MMCostMatrix\GS10MinCLients.shp"
Schools = "Z:\Thesis\Data1\MMCostMatrix\Schools10Clients.shp"
TransitStops = "Z:\Thesis\Data1\MMCostMatrix\TS10Clients.shp"

# Calculate G_Count field
cursor = arcpy.da.SearchCursor(Groceries, ["ORIGINID", "FREQUENCY"])
for row in cursor:
    SQL_statement = "IDNUM = "+ str(row[0])
    frequency = row[1]

    cursor1 = arcpy.da.UpdateCursor(Clients, ["IDNUM", "G_Count"], SQL_statement)
    for row1 in cursor1:
        row1[1] = frequency
        cursor1.updateRow(row1)

del row
del cursor
del row1
del cursor1

# Calculate S_Count field
cursor = arcpy.da.SearchCursor(Schools, ["ORIGINID", "FREQUENCY"])
for row in cursor:
    SQL_statement = "IDNUM = "+ str(row[0])
    frequency = row[1]

    cursor1 = arcpy.da.UpdateCursor(Clients, ["IDNUM", "S_Count"], SQL_statement)
    for row1 in cursor1:
        row1[1] = frequency
        cursor1.updateRow(row1)

del row
del cursor
del row1
del cursor1


# Calculate T_Count field
cursor = arcpy.da.SearchCursor(TransitStops, ["ORIGINID", "FREQUENCY"])
for row in cursor:
    SQL_statement = "IDNUM = "+ str(row[0])
    frequency = row[1]

    cursor1 = arcpy.da.UpdateCursor(Clients, ["IDNUM", "T_Count"], SQL_statement)
    for row1 in cursor1:
        row1[1] = frequency
        cursor1.updateRow(row1)

del row
del cursor
del row1
del cursor1

# Calculate Walkability Score
# classification scheme: 1 point per grocery store, 1 point per school, 0.25 points per transit stop
cursor = arcpy.da.UpdateCursor(Clients, ["G_Count", "S_Count", "T_Count", "WalkScore"])
for row in cursor:
    g_score = row[0]
    s_score = row[1]
    t_score = row[2] * 0.25
    walkscore = g_score + s_score + t_score
    row[3] = walkscore
    cursor.updateRow(row)

del row
del cursor