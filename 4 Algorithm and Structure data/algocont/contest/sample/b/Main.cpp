#include <bits/stdc++.h>

using namespace std;

bool areMatchingPair(char animal, char trap) {
  return toupper(animal) == toupper(trap) && animal != trap;
}

bool processZoo(const string& zoo, vector<int>& result) {
  stack<char> zooStack;
  stack<int> animalPositions, trapPositions;
  unordered_map<int, int> matches;

  int animalIndex = 0, trapIndex = 0;

  for (char c : zoo) {
    if (isupper(c)) {
      trapIndex++;
      trapPositions.push(trapIndex);
    } else {
      animalIndex++;
      animalPositions.push(animalIndex);
    }

    if (zooStack.empty() || !areMatchingPair(zooStack.top(), c)) {
      zooStack.push(c);
    } else {
      matches[trapPositions.top()] = animalPositions.top();
      animalPositions.pop();
      trapPositions.pop();
      zooStack.pop();
    }
  }

  if (!zooStack.empty())
    return false;

  result.resize(trapIndex);
  for (auto& [trap, animal] : matches) {
    result[trap - 1] = animal;
  }

  return true;
}

void printResult(const vector<int>& result) {
  cout << "Possible\n";
  for (int index : result) {
    cout << index << " ";
  }
  cout << "\n";
}

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  string zoo;
  cin >> zoo;

  vector<int> result;
  if (processZoo(zoo, result)) {
    printResult(result);
  } else {
    cout << "Impossible\n";
  }

  return 0;
}
