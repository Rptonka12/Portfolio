#include "VoxelModel.h"
#include <cstdint>
#include <iostream>

//Indexing Functions
uint8_t getIndex(int x, int y, int z) {
	uint8_t index = ((x * 16) + (y * 4) + (z));
	return index;
}

uint8_t getX(uint8_t index) {
	uint8_t xCoord = index / 16;
	return xCoord;
}

uint8_t getY(uint8_t index) {
	uint8_t xVal = getX(index);
	uint8_t yCoord = (index-(xVal*16)) / 4;
	return yCoord;
}
uint8_t getZ(uint8_t index) {
	uint8_t xVal = getX(index);
	uint8_t yVal = getY(index);
	uint8_t zCoord = (index - (4 * yVal)) % 4;
	return zCoord;
}

//Bit Setting Functions
uint64_t emptyModel() {
	uint64_t model = 0; 
	return model;
}

uint64_t fullModel() {
	uint64_t model = 0;
	for (uint64_t i = 0; i < 64; i++) {
		uint64_t one = 1;
		one <<= i;
		model |= one;
	}
	return model;
}

bool getBit(uint64_t model, int x, int y, int z) {
	uint64_t index = getIndex(x, y, z);
	uint64_t tester = 1;
	tester <<= index;
	tester &= model;
	tester >>= index;

	if (tester == 1) { return true; }
	else return false;
}

uint64_t setBit(uint64_t model, int x, int y, int z) {
	uint64_t index = getIndex(x, y, z);
	uint64_t setter = 1;
	setter <<= index;
	model |= setter;
	return model;
}

uint64_t clearBit(uint64_t model, int x, int y, int z) {
	uint64_t index = getIndex(x, y, z);
	uint64_t clear = 1;
	clear <<= index;
	clear = ~clear;
	model &= clear;
	return model;
}

uint64_t toggleBit(uint64_t model, int x, int y, int z) {
	uint64_t index = getIndex(x, y, z);
	uint64_t toggler = 1;
	toggler <<= index;
	model ^= toggler;
	return model;
}