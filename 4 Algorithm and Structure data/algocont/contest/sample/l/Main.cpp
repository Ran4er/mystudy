#include <deque>
#include <iostream>
#include <vector>

using namespace std;

int main() {
  int n, k;
  cin >> n >> k;

  vector<int> arr(n);
  for (int i = 0; i < n; i++) {
    cin >> arr[i];
  }

  deque<int> dq;

  for (int i = 0; i < k; i++) {
    while (!dq.empty() && arr[i] <= arr[dq.back()]) {
      dq.pop_back();
    }
    dq.push_back(i);
  }

  cout << arr[dq.front()];

  for (int i = k; i < n; i++) {
    cout << " ";

    while (!dq.empty() && dq.front() <= i - k) {
      dq.pop_front();
    }

    while (!dq.empty() && arr[i] <= arr[dq.back()]) {
      dq.pop_back();
    }

    dq.push_back(i);

    cout << arr[dq.front()];
  }

  cout << endl;

  return 0;
}