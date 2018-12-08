#include <iostream>
#include "surfaceExtraction.hpp"
#include "VoxelModel.h"

int main() {
	uint64_t model = emptyModel();
	for (int i = 0; i < 64; i += 3) {
		model = toggleBit(model, getX(i), getY(i), getZ(i));
	}
	writeSTL(model, "Voxels.stl");
}