#include <bits/stdc++.h>
using namespace std;

unordered_map<string, int> variables;
stack<vector<pair<string, int>>> history;

int get_value(const string& var) {
  return variables.count(var) ? variables[var] : 0;
}

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  string line;
  while (cin >> line) {
    if (line == "{") {
      history.push({});
    } else if (line == "}") {
      for (auto it = history.top().rbegin(); it != history.top().rend(); ++it) {
        auto& [var, old_value] = *it;
        if (old_value == INT_MIN) {
          variables.erase(var);
        } else {
          variables[var] = old_value;
        }
      }
      history.pop();
    } else {
      size_t eq_pos = line.find('=');
      string var1 = line.substr(0, eq_pos);
      string var2 = line.substr(eq_pos + 1);

      if (!history.empty()) {
        if (!variables.count(var1)) {
          history.top().emplace_back(var1, INT_MIN);
        } else {
          history.top().emplace_back(var1, variables[var1]);
        }
      }

      if (isdigit(var2[0]) || var2[0] == '-') {
        variables[var1] = stoi(var2);
      } else {
        int assigned_value = get_value(var2);
        cout << assigned_value << endl;
        variables[var1] = assigned_value;
      }
    }
  }

  return 0;
}
