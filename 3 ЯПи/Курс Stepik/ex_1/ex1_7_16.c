#include<stdio.h>

int is_whitespace(char c) { return c == ' ' || c == '\t' || c == '\n'; }

int string_count(char* str) {
    int count = 0;
    if(str[0] == '\0'){
        return 0;
    }
    for(char* cur = str; 1; cur = cur + 1) {
        count++;
        if(cur[+1] == '\0') { break; }
    }
    return count;
}

int string_words(char* str)  {
    int sum = 0;
    int k = 1;
    if(str[0] == '\0'){
        return 0;
    }
    for(int i = 0; i < (sizeof(str)/sizeof(str[0]))-1; i++){
        if(is_whitespace(str[i])){
            k=1;
        }
        if(!is_whitespace(str[i])){
            if(k == 1) sum++;
            k=0;
        }
    }
    return sum;
}

int main(){
    char str[] = "";
    printf("%d\n", string_count(str));
    printf("%d", string_words(str));
    return 0;
}