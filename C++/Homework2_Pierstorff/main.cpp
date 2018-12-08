#include <iostream>
#include "VoxelModel.h"

int main() {
	uint64_t model = fullModel(); //initializes new full model
	std::cout << std::hex << model << std::endl;

	//clears corner bits
	model = clearBit(model, 0, 0, 0);
	model = clearBit(model, 3, 0, 0);
	model = clearBit(model, 0, 3, 3);
	model = clearBit(model, 0, 3, 0);
	model = clearBit(model, 3, 3, 3);
	model = clearBit(model, 3, 0, 3);
	model = clearBit(model, 3, 3, 0);
	model = clearBit(model, 0, 0, 3);
	std::cout << std::hex << model << std::endl;

	//toggles entire model
	for (int i = 0; i < 64; i++) {
		int voxX = getX(i);
		int voxY = getY(i);
		int voxZ = getZ(i);
		model = toggleBit(model, voxX, voxY, voxZ);
	}
	std::cout << std::hex << model << std::endl;

	//turns on/sets bits along half of the edges
	for (int i = 0; i < 64; i++) {
		int voxX = getX(i);
		int voxY = getY(i);
		int voxZ = getZ(i);
		if (voxX == 0 | voxY == 0 | voxZ == 0) 
			model = setBit(model, voxX, voxY, voxZ);
	}
	std::cout << std::hex << model << std::endl;

	//gets a specific bit
	if (!getBit(model, 2, 2, 2)) {
		model = setBit(model, 2, 2, 2);
	}

	//final model output: 3 sided box with two "on" voxels in the opposite corner
	std::cout << std::hex << model << std::endl; 
}