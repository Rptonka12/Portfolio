#pragma once
#include <cstdint>
#include "MyArray.h"

class BitVector {
public:
	BitVector(int numBits); //constructor
	//BitVector(const BitVector &); //copy constructor
	~BitVector(); //destructor
	void fillModel();
	void clearModel();
	bool getBit(int index);
	void setBit(int index, int val);
	int size();

	MyArray<uint8_t> bitmap;

private:
	int numBits;
	int numArrays;

	int getByteNumber(int index);
	uint8_t getBitNumber(int index);
};