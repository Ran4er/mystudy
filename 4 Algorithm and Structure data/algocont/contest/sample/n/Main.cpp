#include <bits/stdc++.h>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);

  int n;
  cin >> n;
  vector<int> to(n + 1);
  for (int i = 1; i <= n; i++) {
    cin >> to[i];
  }

  vector<int> mark(n + 1, 0);
  int cycles = 0;

  for (int i = 1; i <= n; i++) {
    if (mark[i] != 0)
      continue;
    int cur = i;
    while (mark[cur] == 0) {
      mark[cur] = i;
      cur = to[cur];
    }
    if (mark[cur] == i) {
      cycles++;
    }
  }

  cout << cycles << "\n";
  return 0;
}
