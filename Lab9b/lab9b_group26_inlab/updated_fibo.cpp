#include <iostream>
#include <stdio.h>
#include <time.h>
using namespace std;

// domain: x >= 0
int fibonacci (int x) {
  if (x <= 1) return x;
  return fibonacci(x-1) + fibonacci(x-2);
  /* think about making this tail recursive (later) */
}

int main (int argc, char* argv[]) {
  clock_t t;
  t = clock();
  int n;
  scanf("%d",&n);
  if (n < 2){ cout<<n<<", "<<1<<", "<<(float(t))/CLOCKS_PER_SEC<<endl; return 0; }
  int num = fibonacci(n);
  t = clock() - t;
  cout<<n<<", "<<num<<", "<<(float(t))/CLOCKS_PER_SEC<<endl;
  return 0;
}
