#include <iostream>

int main() {
	//Populating array from user input
	int numbers[10];
	int numbersLength = sizeof(numbers) / sizeof(numbers[0]);
	std::cout << "Please insert " << numbersLength << " numbers \n";

	for(int i = 0; i < numbersLength; i++) {
		std::cin >> numbers[i];
	}

	//Print array before sort
	std::cout << "Numbers before sort: ";
	for (int i = 0; i < numbersLength; i++) {
		std::cout << " " << numbers[i];
	}
	std::cout << "\n";

	//Sort array
	for(int i = 1; i < numbersLength; i++) {
		int temp = numbers[i];
		int j = i - 1;
		while (j >= 0 && numbers[j] > temp) {
			numbers[j + 1] = numbers[j];
			j--;
		}
		numbers[j + 1] = temp;
	}

	//Print array after sort
	std::cout << "Numbers after sort: ";
	for (int i = 0; i < numbersLength; i++) {
		std::cout << " " << numbers[i];
	}
	std::cout << "\n";
}