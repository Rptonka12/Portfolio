#include "VoxelModel.h"
#include <cstdint>
#include <iostream>
#include <bitset>
#include <math.h>

//Model Functions
VoxelModel allocateModel(int nx, int ny, int nz) { 
	VoxelModel model;
	model.nx = nx;
	model.ny = ny;
	model.nz = nz;

	//allocating dimensions
	int voxels = nx * ny * nz; 
	if (voxels % 8 == 0) { 
		model.length = voxels / 8; 
		model.bitmap = new uint8_t[model.length];
	}
	else { 
		model.length = (voxels / 8) + 1; 
		model.bitmap = new uint8_t[model.length];
	}
	
	//clearing bits
	clearModel(model);
	return model;
}
void clearModel(VoxelModel& model) { 
	for (int i = 0; i < model.length; i++) {
		model.bitmap[i] = 0;
	}
}

void fillModel(VoxelModel& model) { 
	for (int i = 0; i < model.length; i++) {
		model.bitmap[i] = 0xFF;
	}
}

void deleteModel(VoxelModel& model) {
	delete[] model.bitmap;
	model.bitmap = NULL;
	model.length = 0;
	model.nx = NULL;
	model.ny = NULL;
	model.nz = NULL;
}

//Indexing Functions
int getIndex(const VoxelModel& model, int x, int y, int z) {
	int index = ((x * (model.nx * model.ny)) + ((y * model.ny)) + z);
	return index;
}

void getXYZ(const VoxelModel& model, int index, int& x, int& y, int& z) { 
	int xy = model.nx * model.ny;
	x = index / xy;
	y = (index - (x * xy)) / (model.ny);
	z = (index - (y * (model.ny))) % (model.ny);
}

int getByteNumber(int index) { return index / 8; }
uint8_t getBitNumber(int index) { return index % 8; }

//Bit Setting Functions
bool getBit(const VoxelModel& model, int x, int y, int z) {
	int index = getIndex(model, x, y, z);
	int byte = getByteNumber(index);
	int bit = getBitNumber(index);

	uint8_t tester = 1;
	tester <<= (7 - bit);
	tester &= model.bitmap[byte];
	tester >>= (7 - bit);

	if (tester == 1) { return true; }
	else return false;
}

void setBit(VoxelModel& model, int x, int y, int z) {
	int index = getIndex(model, x, y, z); 
	int byte = getByteNumber(index);
	uint8_t bit = getBitNumber(index); 

	uint8_t setter = 1;
	setter <<= (7 - bit);
	model.bitmap[byte] |= setter;
}

void clearBit(VoxelModel& model, int x, int y, int z) {
	int index = getIndex(model, x, y, z);
	int byte = getByteNumber(index);
	int bit = getBitNumber(index);

	uint8_t clear = 1;
	clear <<= (7 - bit);
	clear = ~clear;
	model.bitmap[byte] &= clear;
}

void toggleBit(VoxelModel& model, int x, int y, int z) {
	int index = getIndex(model, x, y, z);
	int byte = getByteNumber(index);
	int bit = getBitNumber(index);

	uint8_t toggler = 1;
	toggler <<= (7 - bit);
	model.bitmap[byte] ^= toggler;
}

//Sphere Functions
bool insideSphere(VoxelModel& model, int index, float cx, float cy, float cz, float radius) {
	int vx, vy, vz;
	getXYZ(model, index, vx, vy, vz);
	float fx = float(vx); 
	float fy = float(vy); 
	float fz = float(vz); 
	float distance = pow((fx - cx), 2) + pow((fy - cy), 2) + pow((fz - cz), 2);
	float rad2 = pow(radius, 2);

	if (distance < rad2) { return true; }
	else return false;
}

void addSphere(VoxelModel& model, float cx, float cy, float cz, float radius) {
	int size = model.nx * model.ny * model.nz;

	for (int i = 0; i < size; i++) {
		int voxX;
		int voxY;
		int voxZ;
		getXYZ(model, i, voxX, voxY, voxZ);

		if (insideSphere(model, i, cx, cy, cz, radius)) {
			setBit(model, voxX, voxY, voxZ);
		}
	}
}

void subtractSphere(VoxelModel& model, float cx, float cy, float cz, float radius) {
	int size = model.nx * model.ny * model.nz;

	for (int i = 0; i < size; i++) {
		int voxX;
		int voxY;
		int voxZ;
		getXYZ(model, i, voxX, voxY, voxZ);

		if (insideSphere(model, i, cx, cy, cz, radius)) {
			clearBit(model, voxX, voxY, voxZ);
		}
	}
}

void toggleSphere(VoxelModel& model, float cx, float cy, float cz, float radius) {
	int size = model.nx * model.ny * model.nz;

	for (int i = 0; i < size; i++) {
		int voxX;
		int voxY;
		int voxZ;
		getXYZ(model, i, voxX, voxY, voxZ);

		if (insideSphere(model, i, cx, cy, cz, radius)) {
			toggleBit(model, voxX, voxY, voxZ);
		}
	}
}