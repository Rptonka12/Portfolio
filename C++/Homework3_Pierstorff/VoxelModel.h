#pragma once
#ifndef FUNCTIONS_H_INCLUDED
#define FUNCTIONS_H_INCLUDED
#include <cstdint>

//Indexing Functions
uint8_t getIndex(int x, int y, int z);

uint8_t getX(uint8_t index);
uint8_t getY(uint8_t index);
uint8_t getZ(uint8_t index);

//Bit Setting Functions
uint64_t emptyModel();
uint64_t fullModel();
bool getBit(uint64_t model, int x, int y, int z);
uint64_t setBit(uint64_t model, int x, int y, int z);
uint64_t clearBit(uint64_t model, int x, int y, int z);
uint64_t toggleBit(uint64_t model, int x, int y, int z);

#endif