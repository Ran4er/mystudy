#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>

// вы можете реализовать эти функции для более удобного считывания чисел
int64_t read_int64();
size_t read_size();

// заполнить уже выделенный массив array размера size числами
// числа нужно считывать из потока ввода
void array_int_fill( int64_t* array, size_t size ) {
  int64_t min = array[0];
  for(int i = 0; i < size; i++){
    if(min > array[i]) min = array[i];
  }
  printf("%" PRId64, min);
}

// Считать размер массива в *size, выделить память под массив и заполнить его числами, вызвав array_int_fill
int64_t* array_int_read( size_t* size ) {
  printf("%zd", *size);
  int64_t* array = malloc(sizeof(int64_t) * (*size));
  for(int i = 0; i < *size; i++){
    scanf("%" SCNd64, array[i]);
  }
  return array;
}

void main(){
  size_t size;
  scanf("%zu", &size);
  array_int_fill(array_int_read(&size), size);

}
