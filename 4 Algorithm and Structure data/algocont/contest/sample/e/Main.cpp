#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

bool canPlaceCows(const vector<int>& stalls, int k, int distance) {
    int count = 1; 
    int lastStall = stalls[0];
    
    for (size_t i = 1; i < stalls.size(); ++i) {
        if (stalls[i] - lastStall >= distance) {
            count++;
            lastStall = stalls[i];
            if (count >= k) {
                return true;
            }
        }
    }
    
    return false;
}

int findMaxMinDistance(const vector<int>& stalls, int k) {
    int left = 0;
    int right = stalls.back() - stalls[0];
    int result = 0;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (canPlaceCows(stalls, k, mid)) {
            result = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}

int main() {
    int n, k;
    cin >> n >> k;
    
    vector<int> stalls(n);
    for (int i = 0; i < n; ++i) {
        cin >> stalls[i];
    }
    
    sort(stalls.begin(), stalls.end());
    
    int maxMinDistance = findMaxMinDistance(stalls, k);
    
    cout << maxMinDistance << endl;
    
    return 0;
}