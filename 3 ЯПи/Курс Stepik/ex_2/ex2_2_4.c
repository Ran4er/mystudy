#include<stdlib.h>
#include<stdio.h>
#include<inttypes.h>

void print_newline(){
    printf("\n");
}

int64_t read_int64(){
    int64_t num;
    scanf("%" SCNd64, &num);
    return num;
}

size_t read_size(){
    size_t size;
    scanf("%zu", &size);
    return size;
}

void array_int_fill( int64_t* array, size_t size ) {

    for(size_t i = 0; i < size; i++){
        array[i] = read_int64();
    }
}

void array_int_print(int64_t* array, size_t size) {
    for(size_t i = 0; i < size; i++){
        printf("%" PRId64 " ", array[i]);
    }
}

int64_t* array_int_read( size_t* size ) {
  
    *size = read_size();

    if(*size == 0) return 0;

    int64_t* arr = malloc(sizeof(int64_t) * (*size));

    array_int_fill(arr, *size);

    return arr;
}


int64_t** marray_read(size_t* rows, size_t** sizes){
    
    *rows = read_size();

    if (*rows == 0) {
        *sizes = NULL;
        return NULL;
    }

    int64_t** marray = malloc(sizeof(int64_t*) * (*rows));
    *sizes = malloc(sizeof(size_t) * (*rows));

    for(size_t i = 0; i < *rows; i++){
        marray[i] = array_int_read(&(*sizes)[i]);
    }

    return marray;

}

void marray_print(int64_t** marray, size_t* sizes, size_t rows){

    for( size_t i = 0; i < rows; i = i + 1 ) {
        array_int_print( marray[i], sizes[i] );
        print_newline();
    }

}

void marray_free(int64_t** marray, size_t rows){
    for(size_t i = 0; i < rows; i++){
        if(marray[i] == 0) continue;
        free(marray[i]);
    }
    free(marray);
}


int main(){
    
    size_t rows;
    size_t* sizes;
    int64_t** marray = marray_read(&rows, &sizes);

    marray_print(marray, sizes, rows);

    // Free allocated memory
    for(size_t i = 0; i < rows; i++){
        free(marray[i]);
    }
    free(marray);
    free(sizes);

    return 0;

}