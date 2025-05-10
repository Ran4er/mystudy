#include <iostream>
#include <set>
#include <unordered_map>
#include <vector>

using namespace std;

typedef long long ll;

struct Interval {
    ll start, end;

    bool operator<(const Interval& other) const {
        return start < other.start;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    ll N;
    int M;
    cin >> N >> M;

    set<Interval> freeBlocks;
    freeBlocks.insert({1, N});

    unordered_map<int, Interval> allocations;
    vector<int> result;

    for (int i = 1; i <= M; ++i) {
        ll req;
        cin >> req;

        if (req > 0) {
            ll K = req;
            bool allocated = false;

            for (auto it = freeBlocks.begin(); it != freeBlocks.end(); ++it) {
                ll l = it->start;
                ll r = it->end;

                if (r - l + 1 >= K) {
                    ll allocStart = l;
                    ll allocEnd = l + K - 1;

                    if (allocStart == 1 || freeBlocks.find({allocStart - 1, allocStart - 1}) == freeBlocks.end()) {
                        result.push_back(allocStart);
                        allocations[i] = {allocStart, allocEnd};

                        freeBlocks.erase(it);
                        if (allocEnd < r) {
                            freeBlocks.insert({allocEnd + 1, r});
                        }
                        if (allocStart > l) {
                            freeBlocks.insert({l, allocStart - 1});
                        }

                        allocated = true;
                        break;
                    }
                }
            }

            if (!allocated) {
                result.push_back(-1);
            }
        } else {
            int T = -req;
            if (allocations.count(T)) {
                Interval interval = allocations[T];
                allocations.erase(T);

                auto it = freeBlocks.lower_bound({interval.start, interval.start});
                if (it != freeBlocks.begin()) {
                    auto prev = prev(it);
                    if (prev->end + 1 == interval.start) {
                        interval.start = prev->start;
                        freeBlocks.erase(prev);
                    }
                }

                it = freeBlocks.lower_bound({interval.start, interval.start});
                if (it != freeBlocks.end() && interval.end + 1 == it->start) {
                    interval.end = it->end;
                    freeBlocks.erase(it);
                }

                freeBlocks.insert(interval);
            }
        }
    }

    for (int r : result) {
        cout << r << '\n';
    }

    return 0;
}
