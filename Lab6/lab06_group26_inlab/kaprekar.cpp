# include <iostream>
# include <vector>
# include <string>
# include <algorithm>
# include <cmath>

using namespace std;

class Digits{
public:
	int val;
	vector <int> v ;
	int as;
	int des;
	int sub;
	int count;

	Digits(int val1){
		val = val1;as = 0 ;des = 0;sub = 0;count = 1;
	}

};

int main(){
	vector<Digits> v1;

	for(int i = 1000 ; i <= 2000 ; i++){
		
		if ((i%1111) != 0 ){
			Digits a(i);int x = i;
			for(int q=0; q<4;q++){ a.v.push_back(x%10); x=x/10;  }

				sort(a.v.begin(),a.v.end());

				for(int b = 0 ; b < 4 ; b++){
					a.des = a.des + a.v[b]*pow(10,b);
					a.as = a.as + a.v[3-b]*pow(10,b);
					
				}
				//cout<<"out"<<endl;cout<<a.as<<endl;cout<<a.des<<endl;
				a.sub = a.des - a.as;
				//cout<< a.sub << endl;
			

				while(a.sub != a.des && a.sub != a.as && a.sub != 6174 ){ 
					a.count++;
					
						int x = a.sub;
						for(int q=0; q<4;q++){ a.v[q] = (x%10); x=x/10;  }

							sort(a.v.begin(),a.v.end());
							a.des = 0;a.as = 0;

							for(int b = 0 ; b < 4 ; b++){
								a.des = a.des + a.v[b]*pow(10,b);
								a.as = a.as + a.v[3-b]*pow(10,b);
								
							}
							//cout<<"out"<<endl;cout<<a.as<<endl;cout<<a.des<<endl;
							a.sub = a.des - a.as;
							//cout<< a.sub << endl;
												
				}
		
		v1.push_back(a);
	}

}//for ends

	for(int i = 0 ; i < v1.size(); i++){
		if(v1[i].val == 6174){//cout<<v1[i].count<<"found ya";}
	}
		//cout<< v1[i].count <<endl;
	}
	std::vector<Digits> v2,v3,v4,v5,v6,v7,v8;

	for(int i = 0 ; i < v1.size(); i++){
		if(v1[i].count == 1){v2.push_back(v1[i]);}
		if(v1[i].count == 2){v3.push_back(v1[i]);}
		if(v1[i].count == 3){v4.push_back(v1[i]);}
		if(v1[i].count == 4){v5.push_back(v1[i]);}
		if(v1[i].count == 5){v6.push_back(v1[i]);}
		if(v1[i].count == 6){v7.push_back(v1[i]);}
		if(v1[i].count == 7){v8.push_back(v1[i]);}
		
	}

	cout<<"1 " << v2.size() <<endl;
	cout<<"2 " << v3.size() <<endl;
	cout<<"3 " << v4.size() <<endl;
	cout<<"4 " << v5.size() <<endl;
	cout<<"5 " << v6.size() <<endl;
	cout<<"6 " << v7.size() <<endl;
	cout<<"7 " << v8.size() <<endl;



}

