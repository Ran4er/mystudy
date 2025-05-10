#include <deque>
#include <iostream>
using namespace std;

int main() {
  int n;
  cin >> n;

  deque<int> first_half, second_half;

  for (int i = 0; i < n; i++) {
    char operation;
    cin >> operation;

    if (operation == '+') {
      int id;
      cin >> id;
      second_half.push_back(id);
    } else if (operation == '*') {
      int id;
      cin >> id;
      second_half.push_front(id);
    } else if (operation == '-') {
      cout << first_half.front() << endl;
      first_half.pop_front();
    }

    while (first_half.size() < second_half.size()) {
      first_half.push_back(second_half.front());
      second_half.pop_front();
    }

    if (first_half.size() > second_half.size() + 1) {
      second_half.push_front(first_half.back());
      first_half.pop_back();
    }
  }

  return 0;
}