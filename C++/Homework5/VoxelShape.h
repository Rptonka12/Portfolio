#pragma once
#include "BitVector.h"

class VoxelShape {
public:
	VoxelShape(int x, int y, int z); //constructor
	~VoxelShape(); //destructor
	void fill();
	void clear();

	//Voxel Functions
	bool getVoxel(int x, int y, int z);
	void setVoxel(int x, int y, int z);
	void clearVoxel(int x, int y, int z);
	void toggleVoxel(int x, int y, int z);

	//Sphere Functions
	void addSphere(float cx, float cy, float cz, float radius);
	void subtractSphere(float cx, float cy, float cz, float radius);
	void toggleSphere(float cx, float cy, float cz, float radius);

	//VoxelShape* resize(int x, int y, int z);

	int shapeSize;
	int mx, my, mz;

private:
	BitVector* storedBits;
	int getIndex(int x, int y, int z);
	void getXYZ(int index, int& x, int& y, int& z);
	bool insideSphere(int index, float cx, float cy, float cz, float radius);
};

void exportModel(const char* filename, VoxelShape& model);