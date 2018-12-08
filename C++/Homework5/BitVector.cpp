#include "BitVector.h"
#include <iostream>

BitVector::BitVector(int bits)
{
	numBits = bits;
	if (numBits % 8 == 0) { 
		numArrays = numBits / 8; 
	} 
	else {
		numArrays = (numBits / 8) + 1;
	}
	bitmap = new uint8_t[numArrays]{0};
}

BitVector::~BitVector()
{
	delete[] bitmap;
	bitmap = NULL;
	numArrays = 0;
	numBits = 0;
}

void BitVector::fillModel()
{
	for (int i = 0; i < numArrays; i++) {
		bitmap[i] = 0xFF;
	}
}

void BitVector::clearModel()
{
	for (int i = 0; i < numArrays; i++) {
		bitmap[i] = 0;
	}
}

bool BitVector::getBit(int index)
{
	int byte = getByteNumber(index);
	int bit = getBitNumber(index);

	uint8_t tester = 1;
	tester <<= (7 - bit);
	tester &= bitmap[byte];
	tester >>= (7 - bit);

	if (tester == 1) { return true; }
	else return false;
}

void BitVector::setBit(int index, int val)
{
	int byte = getByteNumber(index);
	int bit = getBitNumber(index);

	uint8_t setter = val;
	if (setter == 1) {
		setter <<= (7 - bit);
		bitmap[byte] |= setter;
	}
	else {
		uint8_t clear = 1;
		clear <<= (7 - bit);
		clear = ~clear;
		bitmap[byte] &= clear;
	}
}

int BitVector::size() { return numBits; }

//void BitVector::resize(int newSize, const BitVector & source)
//{
//	if (newSize < numBits) {
//		for (int i = 0; i < newSize; i++) {
//			this->bitmap[i] = source.bitmap[i];
//		}
//	}
//	else {
//		for (int i = 0; i < numBits; i++) {
//			this->bitmap[i] = source.bitmap[i];
//		}
//		int extra = newSize - numBits;
//		for (int j = newSize; j < extra; j++) {
//			this->bitmap[j] = 0;
//		}
//	}
//}

int BitVector::getByteNumber(int index) { return index / 8; }
uint8_t BitVector::getBitNumber(int index) { return index % 8; }