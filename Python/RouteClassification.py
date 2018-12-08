# Workspace
import arcpy
from arcpy import env
env.workspace = "Z:\Thesis"
env.overwriteOutput = True

# Variables
Clients = "Z:\Thesis\Client Data\\1MileClients.shp"
TransitClients = "Z:\Thesis\Data1\MMCostMatrix\TS10Clients.shp"
TransitStops = ""