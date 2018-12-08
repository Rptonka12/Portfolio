<!DOCTYPE js>
/* lab11.js */
export class Lab11 {  
  //function 1
  defaultParams(first, second = 100){
    obj = { 'firstAtt': first, 'secondAtt': second };
    return obj;
  }
  //function 2
  templateLiterals(firstName, middleName, lastName) {
    var namestring = `Your name is ${firstName}, ${middleName}, ${lastName}`;
    return namestring;
  }
  //function 3
  multilineStrings() {
    var multilineString = `I am a multiline string.
      Isn’t it great!
      So amazing.
      Can’t believe it.`;
    return multilineString;
  }
  //function 4
  sortWithArrow(numArray){
    return numArray.sort((a, b) => (b - a));
  }
}

//test function 1
var test = defaultParams('hello', 85);
console.log(test);
//test function 2
console.log(templateLiterals("Rachel", "Patricia", "Pierstorff"));
//test function 3
console.log(multilineStrings());
//test function 4
numbers = [5,7,8,3,1,6,8,0,4];
var sorted = sortWithArrow(numbers);
for (var i = 0; i < numbers.length; i++){
  console.log(sorted[i]);
}