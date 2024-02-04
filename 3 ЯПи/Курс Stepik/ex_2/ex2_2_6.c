#include<stdio.h>
#include<stdlib.h>
#include<inttypes.h>

size_t read_size(){
    size_t size;
    scanf("%zu", &size);
    return size;
}

int64_t* array_int_read(size_t* size){
    *size = read_size();
    if (*size == 0) return NULL;

    int64_t* array = malloc(sizeof(int64_t) * (*size));
    for(size_t i = 0; i < *size; i++){
        scanf("%" SCNd64, &array[i]);
    }
    return array;
}

void array_int_print(int64_t* array, size_t size){
    if(array == NULL || size == 0) return;
    for(size_t i = 0; i < size-1; i++){
        printf("%" PRId64 " ", array[i]);
    }
    printf("%" PRId64, array[size-1]);
}

void print_newline(){
    printf("\n");
}

int64_t* array_int_min(int64_t* array, size_t size){
    if(array == NULL || size == 0) return NULL;

    int64_t* min = &array[0];
    for(size_t i = 1; i < size; i++){
        if(array[i] < *min) min = &array[i];
    }
    return min;
}

int64_t** marray_read(size_t* rows, size_t* sizes[]){
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
    for(size_t i = 0; i < rows; i++){
        array_int_print(marray[i], sizes[i]);
        print_newline();
    }
}

void marray_free(int64_t** marray, size_t rows){
    for(size_t i = 0; i < rows; i++){
        if(marray[i] != NULL) free(marray[i]);
    }
    free(marray);
}

int64_t* int64_ptr_min(int64_t* x, int64_t* y) {
    if (x == NULL && y == NULL) return NULL;
    if (x == NULL) return y;
    if (y == NULL) return x;

    return (*x < *y) ? x : y;
}

int64_t* marray_int_min(int64_t** marray, size_t* sizes, size_t rows) {
    if (rows == 0) return NULL;

    int64_t* min_ptr = NULL;
    for (size_t i = 0; i < rows; i++) {
        if(sizes[i] != 0) min_ptr = int64_ptr_min(min_ptr, array_int_min(marray[i], sizes[i]));
    }

    return min_ptr;
}

void marray_normalize(int64_t** marray, size_t sizes[], size_t rows, int64_t m) {
    for (size_t i = 0; i < rows; i++) {
        for (size_t j = 0; j < sizes[i]; j++) {
            marray[i][j] -= m;
        }
    }
}

void perform() {
    size_t rows;
    size_t* sizes;
    int64_t** marray = marray_read(&rows, &sizes);

    int64_t* min_ptr = marray_int_min(marray, sizes, rows);

    if (min_ptr != NULL) {
        marray_normalize(marray, sizes, rows, *min_ptr);
        marray_print(marray, sizes, rows);
    }

    marray_free(marray, rows);

    free(sizes);
}

int main() {
    perform();
    return 0;
}
