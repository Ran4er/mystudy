#include<stdlib.h>
#include<inttypes.h>
#include<stdio.h>

#ifndef _cplusplus
    typedef unsigned char bool;
    static const bool false = 0;
    static const bool true = 1;
#endif

// Один из двух случаев:
// - valid = true и value содержит осмысленный результат
// - valid = false и value может быть любым

struct maybe_int64 {
  bool valid;
  int64_t value;
};

// Первый случай; создаем функцию в помощь
// Не бойтесь за производительность
struct maybe_int64 some_int64( int64_t i ) {
  return (struct maybe_int64) { .value = i, .valid = true };
}

// Второй случай; можно создать не функцию, 
// а константный экземпляр структуры
// Все поля инициализированы нулями
// .value = 0, .valid = false
const struct maybe_int64 none_int64 = { 0 };

// упакованное число 42 выводится как "Some 42"
// Ошибка выводится как None
void maybe_int64_print( struct maybe_int64 i ) {

    if (i.valid == false || i.valid == 0) printf("None");
    else printf("Some %" PRId64, i.value);

}

// Если обе упаковки содержат ошибку, то результат ошибка
// Если ровно одна упаковка содержит ошибку, то результат -- вторая
// Если обе упаковки содержат число, то результат это минимум из двух чисел.
struct maybe_int64 maybe_int64_min(struct maybe_int64 a, struct maybe_int64 b) {
    if(!a.valid && !b.valid) return none_int64;
    if(!a.valid) return b;
    if(!b.valid) return a;
    return (a.value < b.value) ? a : b;
}

int main(){

    struct maybe_int64 a;
    struct maybe_int64 b;

    scanf("%d %" SCNd64, a.valid, a.value);
    scanf("%d %" SCNd64, b.valid, b.value);

    maybe_int64_print(maybe_int64_min(a, b));

    return 0;
}