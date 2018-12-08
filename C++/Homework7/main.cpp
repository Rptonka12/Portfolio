#define _CRT_SECURE_NO_DEPRECATE
#include <iostream>
#include "VoxelShape.h"

int main() {
	VoxelShape test(50, 50, 50);
	test.fill();
	test.subtractSphere(24, 24, 24, 30);
	for (int i = 0; i < 50; i++) {
		test.toggleVoxel(0, 0, 0);
		test.toggleVoxel(0, 0, i);
		test.toggleVoxel(0, i, 0);
		test.toggleVoxel(0, i, i);
		test.toggleVoxel(i, 0, 0);
		test.toggleVoxel(i, 0, i);
		test.toggleVoxel(i, i, 0);
		test.toggleVoxel(i, i, i);
		//test.toggleVoxel(49, 49, 49);
		//test.toggleVoxel(49, 49, i);
		//test.toggleVoxel(49, i, 49);
		//test.toggleVoxel(49, i, i);
		//test.toggleVoxel(i, 49, 49);
		//test.toggleVoxel(i, 49, i);
		//test.toggleVoxel(i, i, 49);
	}

	exportModel("ClassedVoxels.stl", test);
}