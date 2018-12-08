#pragma once
#ifndef FUNCTIONS_H_INCLUDED
#define FUNCTIONS_H_INCLUDED
#include <cstdint>

//Voxel Model Struct
struct VoxelModel {
	int nx;
	int ny;
	int nz;
	int length;
	uint8_t* bitmap = NULL;
};

//Model Functions
VoxelModel allocateModel(int nx, int ny, int nz);
void clearModel(VoxelModel& model); 
void fillModel(VoxelModel& model); 
void deleteModel(VoxelModel& model); 

//Indexing Functions
int getIndex(const VoxelModel& model, int x, int y, int z);
void getXYZ(const VoxelModel& model, int index, int& x, int& y, int& z);
int getByteNumber(int index);
uint8_t getBitNumber(int index);

//Bit Setting Functions
bool getBit(const VoxelModel& model, int x, int y, int z);
void setBit(const VoxelModel& model, int x, int y, int z);
void clearBit(const VoxelModel& model, int x, int y, int z);
void toggleBit(const VoxelModel& model, int x, int y, int z);

//Sphere Functions
bool insideSphere(VoxelModel& model, int index, float cx, float cy, float cz, float radius);
void addSphere(VoxelModel& model, float cx, float cy, float cz, float radius); 
void subtractSphere(VoxelModel& model, float cx, float cy, float cz, float radius);
void toggleSphere(VoxelModel& model, float cx, float cy, float cz, float radius);

#endif