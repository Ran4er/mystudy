#include <bits/stdc++.h>
using namespace std;
using ll = long long;

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  ll a, b, c, d, k;
  cin >> a >> b >> c >> d >> k;

  ll current = a;

  for (ll day = 0; day < k; day++) {
    ll next = current * b;
    if (next <= c) {
      cout << "0\n";
      return 0;
    }

    next -= c;
    if (next > d)
      next = d;

    if (next == current) {
      cout << current << "\n";
      return 0;
    }

    current = next;
  }

  cout << current << "\n";
  return 0;
}
