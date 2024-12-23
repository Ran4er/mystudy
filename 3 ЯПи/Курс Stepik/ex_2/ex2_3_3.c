#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <stdbool.h>

/* Вы можете пользоваться этими функциями из предыдущих заданий */
size_t read_size() { size_t i; scanf("%zu", &i); return i; }

int64_t read_int64() {
    int64_t num;
    scanf("%" SCNd64, &num);
    return num;
}

void array_int_fill( int64_t* array, size_t sz ) {
  for( size_t i = 0; i < sz; i = i + 1 ) {
    array[i] = read_int64();
  }
}

struct maybe_int64 {
  int64_t value;
  bool valid;
};

struct array_int {
    int64_t* data;
    size_t size;
};

const struct maybe_int64 none_int64 = { 0 };

struct array_int array_int_read() {
  const size_t size = read_size();
  if (size > 0) {
    int64_t* array = malloc( sizeof(int64_t) * size);
    array_int_fill( array, size );
    return (struct array_int) { .data = array, .size = size };
  }
  else return (struct array_int) {0};
}
struct maybe_int64 array_int_get( struct array_int a, size_t i ) {
    
    if(i > a.size) return none_int64;
    struct maybe_int64 getter = { (a.data)[i], 1 };
    return getter;
}
bool array_int_set( struct array_int a, size_t i, int64_t value ) {

    if(i > a.size) return false;
    (a.data)[i] = value;
    return true;

}
void array_int_print( struct array_int array ) {
  for (size_t i = 0; i < array.size; i = i + 1) {
    printf("%" PRId64 " " , array_int_get( array, i).value);
  }
}
struct maybe_int64 array_int_min( struct array_int array ) {

    if (array.size == 0) {
        return none_int64;
    }
    int64_t min = array.data[0];
    for (size_t i = 1; i < array.size; i++) {
        if (array.data[i] < min) {
            min = array.data[i];
        }
    }
    return (struct maybe_int64){min, true};

}
void array_int_free( struct array_int a ) { 
    if ( a.size > 0 ) {
        free(a.data); 
        a.size = 0;
    } 
}

void array_int_normalize( struct array_int array, int64_t m ) {
  for (size_t i = 0; i < array.size; i++) {
    array.data[i] -= m;
  }
}

/*  ---- maybe int[] ---- */

struct maybe_array_int {
  struct array_int value;
  bool valid;
};

struct maybe_array_int some_array_int(struct array_int array) {
  return (struct maybe_array_int) { array, true };
}
const struct maybe_array_int none_array_int = { {NULL, 0}, 0 };


/*  ---- int[][] ---- */

struct array_array_int {
  struct array_int* data;
  size_t size;
};

/*  --- строки ---  */

struct maybe_array_int array_array_int_get_row( struct array_array_int a, size_t i ) {
  if ( 0 <= i && i < a.size ) { return some_array_int( a.data[i] ); }
  else { return none_array_int; }
}

bool array_array_int_set_row( struct array_array_int a, size_t i, struct array_int value ) {
  if (0 <= i && i < a.size) {
    a.data[i] = value;
    return true;
  }
  else { return false; }
}

/*  --- get/set ---  */

struct maybe_int64 array_array_int_get( struct array_array_int a, size_t i, size_t j ) {

  if (i < a.size && j < a.data[i].size) {
    return array_int_get(a.data[i], j);
  } else {
    return none_int64;
  }

}

bool array_array_int_set( struct array_array_int a, size_t i, size_t j, int64_t value ) {

  if (i < a.size && j < a.data[i].size) {
    return array_int_set(a.data[i], j, value);
  } else {
    return false;
  }

}

/*  --- read/print ---  */

struct array_array_int array_array_int_read() {

  const size_t rows = read_size();
  struct array_array_int result;
  result.size = rows;
  result.data = malloc(sizeof(struct array_int) * rows);
  for (size_t i = 0; i < rows; i++) {
    result.data[i] = array_int_read();
  }
  return result;
  

}


void array_array_int_print( struct array_array_int array) {

  for (size_t i = 0; i < array.size; i++) {
    array_int_print(array.data[i]);
    printf("\n");
  }

}


/*  --- min/normalize ---  */

// найти минимальный элемент в массиве массивов
struct maybe_int64 array_array_int_min( struct array_array_int array ) {

  if( array.size == 0 ) return none_int64; 
  int64_t min = array.data[0].data[0];
    for (size_t i = 0; i < array.size; i++) {
        for (size_t j = 0; j < array.data[i].size; j++) {
            if (array.data[i].data[j] < min) {
                min = array.data[i].data[j];
            }
        }
    }
    return (struct maybe_int64){min, true};

}

// вычесть из всех элементов массива массивов число m
void array_array_int_normalize( struct array_array_int array, int64_t m) {
  for (size_t i = 0; i < array.size; i = i + 1) {
    const struct maybe_array_int cur_row = array_array_int_get_row( array, i );
    if (cur_row.valid) {
         array_int_normalize( cur_row.value, m );
    }
  }
}

void array_array_int_free( struct array_array_int array ) {

  if ( array.size > 0 ) {

    for(size_t i = 0; i < array.size; i++) {

      array_int_free((array.data)[i]);

    }
    
    free( array.data );
    array.size = 0;
  }

}

void perform() {
  struct array_array_int array = array_array_int_read();
  struct maybe_int64 m = array_array_int_min( array );
  if (m.valid) {
    array_array_int_normalize( array, m.value );
    array_array_int_print( array );
  }
  array_array_int_free( array );
}

/*
Test case:
3
9 3 2 4 54 9 2 1 872 123
8 123 12354 23 232 43412 534 8237 -99292
3 45 2 245
*/

int main(){

  perform();

  return 0;
}