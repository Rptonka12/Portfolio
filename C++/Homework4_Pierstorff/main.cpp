#include <iostream>
#include "surfaceExtraction.hpp"
#include "VoxelModel.h"

int main() {
	VoxelModel model = allocateModel(40, 40, 40);
	fillModel(model);
	subtractSphere(model, 2, 2, 2, 10);
	subtractSphere(model, 38, 38, 38, 10);
	toggleSphere(model, 20, 20, 20, 25);
	addSphere(model, 20, 20, 20, 15);
	writeSTL(model, "Voxels.stl");
}