/*
* Copyright (c) 2006-2009 Erin Catto http://www.box2d.org
*
* This software is provided 'as-is', without any express or implied
* warranty.  In no event will the authors be held liable for any damages
* arising from the use of this software.
* Permission is granted to anyone to use this software for any purpose,
* including commercial applications, and to alter it and redistribute it
* freely, subject to the following restrictions:
* 1. The origin of this software must not be misrepresented; you must not
* claim that you wrote the original software. If you use this software
* in a product, an acknowledgment in the product documentation would be
* appreciated but is not required.
* 2. Altered source versions must be plainly marked as such, and must not be
* misrepresented as being the original software.
* 3. This notice may not be removed or altered from any source distribution.
*/

/* 
 * Base code for CS 251 Software Systems Lab 
 * Department of Computer Science and Engineering, IIT Bombay
 * 
 */


#include "cs251_base.hpp"
#include "render.hpp"
#include "cmath"

#ifdef __APPLE__
	#include <GLUT/glut.h>
#else
	#include "GL/freeglut.h"
#endif

#include <cstring>
using namespace std;

#include "dominos.hpp"
#define DEGTORAD 0.0174532925199432957f
#define RADTODEG 57.295779513082320876f

namespace cs251
{
  /**  The is the constructor 
   * This is the documentation block for the constructor.
   */ 
  
  dominos_t::dominos_t()
  {
    //Ground
    /*! \var b1 
     * \brief pointer to the body ground 
     */ 
    b2Body* b1;  //! b1:b2Body pointer ,ground pointer
    {
      
      b2EdgeShape shape; /// shape:b2EdgeShape ,ground line
      shape.Set(b2Vec2(-90.0f, 0.0f), b2Vec2(90.0f, 0.0f));
      b2BodyDef bd; 
      b1 = m_world->CreateBody(&bd); 
      b1->CreateFixture(&shape, 0.0f);
    }
    //present working
    {b2PolygonShape shape100;
      shape100.SetAsBox(180.0f, 0.2f);
      b2BodyDef bd200;
      bd200.position.Set(0.0f, 0.5f);
      
      b2Body* body100 = m_world->CreateBody(&bd200);
      b2FixtureDef *fd200 = new b2FixtureDef;
      fd200->density = 1.f;
      fd200->shape = new b2PolygonShape;
      fd200->shape = &shape100;
      body100->CreateFixture(fd200);
      
      b2PolygonShape shape200;
      shape200.SetAsBox(180.0f, 0.2f);
      b2BodyDef bd400;
      bd400.position.Set(0.0f, 30.0f);
      
      b2Body* body200 = m_world->CreateBody(&bd400);
      b2FixtureDef *fd400 = new b2FixtureDef;
      fd400->density = 1.f;
      fd400->shape = new b2PolygonShape;
      fd400->shape = &shape200;
      body200->CreateFixture(fd400);
  }
      
      ///// bike
      {float xpos=45.0 , ypos=38.0;
	{
		//front wheel
		b2Body* sbody;/// sbody : b2Body pointer,Front Wheel of Cycle.
		  b2CircleShape circle;/// circle :b2CircleShape , Shape of front wheel of Cycle 
      circle.m_radius = 3.0;
	
      b2FixtureDef ballfd;/// ballfd: b2FixtureDef , Fixture of Front wheel of cycle
      ballfd.shape = &circle;
      ballfd.density = 0.2f;
      ballfd.friction = 0.5f;
      ballfd.restitution = 0.0f;
      b2BodyDef ballbd;/// ballbd: b2BodyDef , Body Definition of Front wheel of cycle
      ballbd.type = b2_dynamicBody;
      ballbd.position.Set(xpos, ypos);
      sbody = m_world->CreateBody(&ballbd);
      sbody->CreateFixture(&ballfd);
      sbody->SetLinearVelocity(b2Vec2(-5,0));
      sbody->SetAngularVelocity(55/7);
  
      
      //rear wheel
      b2Body* sbody1;/// sbody1 : b2Body pointer,Back Wheel of Cycle.
      b2CircleShape circle1;/// circle1 :b2CircleShape , Shape of back wheel of Cycle
      circle1.m_radius = 3.0;
	
      b2FixtureDef ballfd1;/// ballfd1: b2FixtureDef , Fixture of Back wheel of cycle
      ballfd1.shape = &circle1;
      ballfd1.density = 0.2f;
      ballfd1.friction = 0.5f;
      ballfd1.restitution = 0.0f;
      b2BodyDef ballbd1;/// ballbd1: b2BodyDef , Body Definition of Front wheel of cycle
      ballbd1.type = b2_dynamicBody;
      ballbd1.position.Set(xpos+10.0, ypos);
      sbody1 = m_world->CreateBody(&ballbd1);
      sbody1->CreateFixture(&ballfd1);
      sbody1->SetLinearVelocity(b2Vec2(-5,0));
      sbody1->SetAngularVelocity(55/7);
	
	
	//pedal
	 b2Body* sbody2;/// sbody2 : b2Body pointer,PEDAL of Cycle.
      b2CircleShape circle2;/// circle2 : b2CircleShape, Shape of PEDAL of Cycle.
      circle2.m_radius = 1.0;
	
      b2FixtureDef ballfd2;/// ballfd2 : b2FixtureDef, Fixture of PEDAL of Cycle.
      ballfd2.shape = &circle2;
      ballfd2.density = 0.2f;
      ballfd2.friction = 1.0f;
      ballfd2.restitution = 0.0f;
      b2BodyDef ballbd2;/// ballbd2 : b2BodyDef, Body Definition of PEDAL of Cycle.
      ballbd2.type = b2_dynamicBody;
      ballbd2.position.Set(xpos+5, ypos+2);
      sbody2 = m_world->CreateBody(&ballbd2);
      sbody2->CreateFixture(&ballfd2);
      
		
		//cycle seat
		b2Body* b1;/// b1 : b2Body pointer,Seat of Cycle.
      
	b2PolygonShape shape1;/// shape1 : b2PolygonShape, Shape of Seat of Cycle
	shape1.SetAsBox(0.8f, 0.25f);//length width
	b2BodyDef bd1;/// bd1 : b2BodyDef, Body Definition of Seat of Cycle.
	bd1.type = b2_dynamicBody;
	bd1.position.Set(xpos+7.0, ypos+5.0);
	b1 = m_world->CreateBody(&bd1);
	b1->CreateFixture(&shape1, 2.0f);

	//handle
	b2Body* b2;/// b2 : b2Body pointer,Handle of Cycle.
	b2PolygonShape shape2;/// shape2 : b2PolygonShape, Shape of Handle of Cycle
	shape2.SetAsBox(1.5f, 0.25f);//length width
	b2BodyDef bd2;/// bd2 : b2BodyDef, Body Definition of Handle of Cycle.
	bd2.type = b2_dynamicBody;
	bd2.position.Set(xpos+2.5, ypos+5.5);
	b2 = m_world->CreateBody(&bd2);
	b2->CreateFixture(&shape2, 2.0f);
	
	
	//seat to pedal
	b2WeldJointDef weldJointDef1;///weldJointDef1 :b2WeldJointDef, Weld joint of cycle between seat and pedal
    weldJointDef1.bodyA = sbody2;
    weldJointDef1.bodyB = b1;

    weldJointDef1.localAnchorA = weldJointDef1.bodyA->GetLocalPoint(b2Vec2(xpos+5,ypos+2));
    weldJointDef1.localAnchorB = weldJointDef1.bodyB->GetLocalPoint(b2Vec2(xpos+7,ypos+5));
    weldJointDef1.collideConnected = true;
    weldJointDef1.Initialize(sbody2, b1, b2Vec2(xpos+5,ypos+2));/// Initialize function:b2weldJointDef,initialises the joint
    m_world->CreateJoint(&weldJointDef1);	
		
	
	//pedal to handle
	b2WeldJointDef weldJointDef2;///weldJointDef2 :b2WeldJointDef, Weld joint of cycle between pedal and handle
    weldJointDef2.bodyA = sbody2;
    weldJointDef2.bodyB = b2;

    weldJointDef2.localAnchorA = weldJointDef1.bodyA->GetLocalPoint(b2Vec2(xpos+5,ypos+2));
    weldJointDef2.localAnchorB = weldJointDef1.bodyB->GetLocalPoint(b2Vec2(xpos+2.5,ypos+5.5));
    weldJointDef2.collideConnected = true;
    weldJointDef2.Initialize(sbody2, b2, b2Vec2(xpos+5,ypos+2));/// Initialize function:b2weldJointDef,initialises the joint
    m_world->CreateJoint(&weldJointDef2);
	
	//handle to seat
	b2DistanceJointDef jointDef5;///jointDef5 :b2DistanceJointDef, Distance joint of cycle between seat and handle	
	jointDef5.Initialize(b1, b2, b2Vec2(xpos+7,ypos+5), b2Vec2(xpos+2.5,ypos+5.5));/// Initialize function:b2DistanceJointDef,initialises the joint
	jointDef5.collideConnected = true;
	m_world->CreateJoint(&jointDef5);
		
	//pedal to rear tire
	b2DistanceJointDef jointDef6;///jointDef6 :b2DistanceJointDef, Distance joint of cycle between pedal and rear tire	
	jointDef6.Initialize(sbody2, sbody1, b2Vec2(xpos+5,ypos+2), b2Vec2(xpos+10,ypos));/// Initialize function:b2DistanceJointDef,initialises the joint
	jointDef6.collideConnected = true;
	m_world->CreateJoint(&jointDef6);
	
	//pedal to front tire
	b2DistanceJointDef jointDef7;///jointDef7 :b2DistanceJointDef, Distance joint of cycle between pedal and front-tire	
	jointDef7.Initialize(sbody2, sbody, b2Vec2(xpos+5,ypos+2), b2Vec2(xpos,ypos));/// Initialize function:b2DistanceJointDef,initialises the joint
	jointDef7.collideConnected = true;
	m_world->CreateJoint(&jointDef7);
	
	//handle to rear wheel
	b2DistanceJointDef jointDef3;///jointDef3 :b2DistanceJointDef, Distance joint of cycle between rear wheel and handle	
	jointDef3.Initialize(sbody1, b2, b2Vec2(xpos+10,ypos), b2Vec2(xpos+2.5,ypos+5.5));/// Initialize function:b2DistanceJointDef,initialises the joint
	jointDef3.collideConnected = true;
	jointDef3.frequencyHz = 1.0f;
	jointDef3.dampingRatio = 1.4f;
	m_world->CreateJoint(&jointDef3);
	
	//trash joints
	//b2RevoluteJointDef jd1;//joint 2
      b2Vec2 anchor1;
      anchor1.Set(xpos, ypos);
      //jd1.Initialize(sbody1,b40, anchor);
      //m_world->CreateJoint(&jd1); */
      
      
	// front suspension (front wheel to handle)
	b2DistanceJointDef jointDef1;///jointDef1 :b2DistanceJointDef, Distance joint of cycle between front wheel and handle	
	jointDef1.Initialize(sbody, b2, b2Vec2(xpos,ypos), b2Vec2(xpos+2.5,ypos+5.5));/// Initialize function:b2DistanceJointDef,initialises the joint
	jointDef1.collideConnected = true;
	jointDef1.frequencyHz = 1.0f;
	jointDef1.dampingRatio = 1.4f;
	m_world->CreateJoint(&jointDef1);

	//back suspension( rear wheel to seat)
	b2DistanceJointDef jointDef2;///jointDef2 :b2DistanceJointDef, Distance joint of cycle between seat and rear wheel	
	jointDef2.Initialize(sbody1, b1, b2Vec2(xpos + 10,ypos), b2Vec2(xpos+7,ypos+5.0));/// Initialize function:b2DistanceJointDef,initialises the joint
	jointDef2.collideConnected = true;
	jointDef2.frequencyHz = 1.5f;
	jointDef2.dampingRatio = 1.4f;
	m_world->CreateJoint(&jointDef2);

	
	//b2RevoluteJointDef jd2;//joint 3
      //jd2.Initialize(sbody, b40, anchor1);
      //m_world->CreateJoint(&jd2);
      
      
      
  
  }
  
  
      
      //boxes
      {for(int q=0; q<48;q++){
      b2PolygonShape shape2;/// shape2: b2PolygonShape, the shape attribute of the box 
      float side;side = 2;
      shape2.SetAsBox(side, side);/// SetAsBox : b2PolygonShape , sets the co-ordinates of each box
      b2BodyDef bd3;/// bd3: b2BodyDef , Body Definition of each box
      bd3.position.Set(-40.0f+ 2*(side)*(q%4), 2*side*(q/4)-2);
      bd3.type = b2_dynamicBody;
         
      b2Body* body3 = m_world->CreateBody(&bd3);/// body3 : b2Body pointer , Creates the boxes in the world
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 0.0001f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape2;
      body3->CreateFixture(fd3);/// CreateFixture:b2Body , Creates the Fixture for each box in the wall}
  }
		  }
	
	// car
	{b2BodyDef carbd;
	  b2FixtureDef carfd;
	  	      
	  carbd.type = b2_dynamicBody; //this will be a dynamic body
	  carbd.angle = 0; //set the starting angle
	  carfd.density = 10; //set the starting angle
	  
	  int x = 55,y = 10;
	  b2Vec2 vertices[11]; 
	  vertices[0].Set(x,  y);
	  vertices[1].Set(x-5,  y);
	  vertices[2].Set(x-6, y-2);
	  vertices[3].Set(x-12, y-5);
	  vertices[4].Set(x-12, y-6);
	  vertices[5].Set(x+2, y-6);
	  vertices[6].Set(x+2, y-2);
	  	  
	  b2PolygonShape polygonShape;
	  polygonShape.Set(vertices, 7); //pass array to the shape
	  
	  carfd.shape = &polygonShape; //change the shape of the fixture
	  
	  b2Body* car = m_world->CreateBody(&carbd);
	  car->SetLinearVelocity(b2Vec2(-5,0));
	  car->CreateFixture(&carfd); //add a fixture to the body
	  
	  b2Vec2 pos( x-2.5, y ); //top
      polygonShape.SetAsBox(0.5, 0.5, pos, 0 ); //a 1x1 rectangle
      car->CreateFixture(&carfd); //add a fixture to the body
      
      b2Vec2 pos1( x+2-1, y-6.5); //below 3
      polygonShape.SetAsBox(1, 0.5, pos1, 0 ); //a 1x1 rectangle
      car->CreateFixture(&carfd); //add a fixture to the body
      
      b2Vec2 pos2( x+2-5-2, y-6.5 ); 
      polygonShape.SetAsBox(2, 0.5, pos2, 0 ); //a 1x1 rectangle
      car->CreateFixture(&carfd); //add a fixture to the body
      
      b2Vec2 pos3( x+2-12-1, y-6.5 ); 
      polygonShape.SetAsBox(1, 0.5, pos3, 0 ); //a 1x1 rectangle
      car->CreateFixture(&carfd); //add a fixture to the body
     ///////////////////////////////////// 
      b2BodyDef wheelbd1;
	  b2FixtureDef wheelfd1;
	  wheelbd1.type = b2_dynamicBody; //this will be a dynamic body
	  wheelfd1.density = 3; 
	  
      b2CircleShape circleShape1;
	  circleShape1.m_p.Set(x+2-2-1.5, y-7.5); //position, relative to body position
	  circleShape1.m_radius = 1.5; //radius
	  
	  wheelfd1.shape = &circleShape1;
	  
	   b2Body* wheel1 = m_world->CreateBody(&wheelbd1);
	   wheel1->SetLinearVelocity(b2Vec2(-15,0));
       wheel1->SetAngularVelocity(11/7);
	   wheel1->CreateFixture(&wheelfd1);
	   
	   b2BodyDef wheelbd2;
	  b2FixtureDef wheelfd2;
	  wheelbd2.type = b2_dynamicBody; //this will be a dynamic body
	  wheelfd2.density = 3; 
	  
      b2CircleShape circleShape2;
	  circleShape2.m_p.Set(x+2-9-1.5, y-7.5); //position, relative to body position
	  circleShape2.m_radius = 1.5; //radius
	  
	  wheelfd2.shape = &circleShape2;
	  
	   b2Body* wheel2 = m_world->CreateBody(&wheelbd2);
	   wheel2->SetLinearVelocity(b2Vec2(-15,0));
       wheel2->SetAngularVelocity(11/7);
	   wheel2->CreateFixture(&wheelfd2);
      
      b2DistanceJointDef joint1;///joint1 :b2DistanceJointDef, Distance joint of car between car and wheel1
      joint1.Initialize(car,wheel1,b2Vec2(x-1.5,y-7.5),b2Vec2(x-1.5,y-7.5));/// Initialize function:b2DistanceJointDef,initialises the joint
      joint1.collideConnected = true;
      m_world->CreateJoint(&joint1);
      
      b2DistanceJointDef joint2;///joint2 :b2DistanceJointDef, Distance joint of car between car and wheel2
      joint2.Initialize(car,wheel2,b2Vec2(x-8.5,y-7.5),b2Vec2(x-8.5,y-7.5));/// Initialize function:b2DistanceJointDef,initialises the joint
      joint2.collideConnected = true;
      m_world->CreateJoint(&joint2);
      
	  }
	
	
	
	//////tank  
	{  float scale = 0.5;
	  b2Body* sbody;/// sbody : b2Body pointer,rear Wheel of Tank. 
      b2CircleShape circle;/// circle: b2CircleShape, shape of rear Wheel of Tank
      circle.m_radius = 5.0;
	
      b2FixtureDef ballfd;/// ballfd : b2FixtureDef, Fixture of rear wheel of Tank
      ballfd.shape = &circle;
      ballfd.density = 50.0f;
      ballfd.friction = 0.0f;
      ballfd.restitution = 0.0f;
      b2BodyDef ballbd;/// ballbf : b2BodyDef, Body Definition of rear Wheel of Tank
      ballbd.type = b2_dynamicBody;
      ballbd.position.Set(30.0f, 10.0f);
      sbody = m_world->CreateBody(&ballbd);
      sbody->CreateFixture(&ballfd);
	  sbody->SetAngularVelocity(-2.5f);
     
      b2Body* sbody2;/// sbody : b2Body pointer,Front Wheel of Tank.
      
      b2BodyDef ballbd2;/// ballbd2 : b2BodyDef, Body Definition of front Wheel of Tank
      ballbd2.type = b2_dynamicBody;
      ballbd2.position.Set(2.0f, 10.0f);
      sbody2 = m_world->CreateBody(&ballbd2);
      sbody2->CreateFixture(&ballfd);
	  sbody2->SetAngularVelocity(-2.5f);
     
     
     
      b2PolygonShape shape;         /// shape: b2PolygonShape, Shape of thorns on the tire
      shape.SetAsBox(1.0f, 0.02f);
      
      b2FixtureDef *fd2 = new b2FixtureDef;/// fd2 : b2FixtureDef pointer, Fixture of thorns on wheel of Tank
      fd2->density = 1.f;
      fd2->shape = new b2PolygonShape;
      fd2->shape = &shape;
         
      for( int i=0; i<8 ; i++){      /// creation of thorns for rear wheel
			b2BodyDef bd2;///bd2: b2BodyDef , Body Definition of a thorn on the wheel
			bd2.position.Set(30.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4));
			bd2.type = b2_dynamicBody;
			b2Body* body = m_world->CreateBody(&bd2);
			body->CreateFixture(fd2);
			body->SetTransform(b2Vec2(30.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4)), i*3.14/4);
			
			b2WeldJointDef jd; ///jd: b2WeldJointDef, Weld joint between thorn and the wheel
			jd.Initialize(sbody,body,b2Vec2(30.0f,10.0f));
			m_world->CreateJoint(&jd);
	
		}
	
	for( int i=0; i<8 ; i++){     /// creation of thorns for front wheel
			b2BodyDef bd2;/// bd2: b2BodyDef , Body Definition of a thorn on the wheel
			bd2.position.Set(2.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4));
			bd2.type = b2_dynamicBody;
			b2Body* body = m_world->CreateBody(&bd2);
			body->CreateFixture(fd2);
			body->SetTransform(b2Vec2(2.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4)), i*3.14/4);
			
			b2WeldJointDef jd;///jd: b2WeldJointDef, Weld joint between thorn and the wheel
			jd.Initialize(sbody2,body,b2Vec2(2.0f,10.0f));
			m_world->CreateJoint(&jd);
	
		}
	
		b2PolygonShape shape2;      // shape2:b2PolygonShape, shape for the chain bits
      shape2.SetAsBox(1.35f , 0.5f);
      b2FixtureDef *fd3 = new b2FixtureDef; // fd3:b2FixtureDef pointer, Fixture of the chain bits
      fd3->density = 0.1f;
      fd3->friction = 0.0f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape2;
      
      b2BodyDef bd3;
      b2Body* body[8];
      b2Body* body2[7];
      b2Body* body3[7];
      b2DistanceJointDef jd[8];
      b2DistanceJointDef jd2[8];
      b2DistanceJointDef jd3[8];

      for(int j=0; j<8; j++){                 /// creating the chain
		if(j<2 || j>=6){			///chain elements on the rear wheel
		bd3.position.Set(30.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8));
		bd3.type = b2_dynamicBody;
		body[j] = m_world->CreateBody(&bd3);
		body[j]->CreateFixture(fd3);
		if(j==0 || j==4 || j==3 || j==7){body[j]->SetTransform(b2Vec2(30.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)), 3.14/2);}
	    if(j != 0 && j!= 6){
			jd[j-1].Initialize(body[j-1],body[j],b2Vec2(30.0f + 5.0f*cos(((j-1)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((j-1)*3.14/4)+3.14/8)), b2Vec2(30.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)));
			m_world->CreateJoint(&jd[j-1]);
		}
		if(j==7){ jd[7].Initialize(body[7],body[0],b2Vec2(30.0f + 5.0f*cos(((7)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((7)*3.14/4)+3.14/8)), b2Vec2(30.0f + 5.0f*cos((0*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((0*3.14/4)+3.14/8)));
				  m_world->CreateJoint(&jd[7]);
                 }
	  }
	  else{				///chain elements on the front wheel
			bd3.position.Set(2.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8));
			bd3.type = b2_dynamicBody;
			body[j] = m_world->CreateBody(&bd3);
			body[j]->CreateFixture(fd3);
			if(j==0 ||j==4 || j==3 || j==7){body[j]->SetTransform(b2Vec2(2.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)), 3.14/2);}
			
			if(j != 2 ){
				jd[j-1].Initialize(body[j-1],body[j],b2Vec2(2.0f + 5.0f*cos(((j-1)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((j-1)*3.14/4)+3.14/8)), b2Vec2(2.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)));
				m_world->CreateJoint(&jd[j-1]);
			}
		
		
	 }
	  
	  }
	  
	  for(int i=0; i<7 ; i++ ){                /// chain elements at the center
		   bd3.position.Set(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ i*3.9f, 15.0f);
		   bd3.type = b2_dynamicBody;
		   body2[i] = m_world->CreateBody(&bd3);
		   body2[i]->CreateFixture(fd3);
		   if(i != 0){jd2[i].Initialize(body2[i-1], body2[i],b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ (i-1)*3.9f, 15.0f), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ i*3.9f, 15.0f));
					m_world->CreateJoint(&jd2[i]);
				}
		   
		   
		   bd3.position.Set(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ i*3.9f, 5.0f);
		   bd3.type = b2_dynamicBody;
		   body3[i] = m_world->CreateBody(&bd3);
		   body3[i]->CreateFixture(fd3);
			if(i != 0){jd3[i].Initialize(body3[i-1], body3[i],b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ (i-1)*3.9f, 5.0f), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ i*3.9f, 5.0f));
					m_world->CreateJoint(&jd3[i]);
				}
		  }
		// left -over joints
			jd2[0].Initialize(body[2], body2[0],b2Vec2(2.0f + 5.0f*cos(((2)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((2)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 0*3.9f, 15.0f));
					m_world->CreateJoint(&jd2[0]);
			jd2[7].Initialize(body[1], body2[6],b2Vec2(30.0f + 5.0f*cos(((1)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((1)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 6*3.9f, 15.0f));
					m_world->CreateJoint(&jd2[7]);
			jd3[0].Initialize(body[5], body3[0],b2Vec2(2.0f + 5.0f*cos(((5)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((5)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 0*3.9f, 5.0f));
					m_world->CreateJoint(&jd3[0]);
			jd3[7].Initialize(body[6], body3[6],b2Vec2(30.0f + 5.0f*cos(((6)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((6)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 6*3.9f, 5.0f));
					m_world->CreateJoint(&jd3[7]);
			
					
			b2PolygonShape shape20;   ///shape20:b2PolygonShape, shape of tank main body
			shape20.SetAsBox(15.0f, 5.0f);
			b2BodyDef bd30; /// bd30: b2BodyDef, Body Definition of the tank main body
			bd30.position.Set(15.0f, 22.0f);
			bd30.type = b2_dynamicBody;
			b2Body* body30 = m_world->CreateBody(&bd30);
			b2FixtureDef *fd30 = new b2FixtureDef; ///fd30: b2FixtureDef, Fixture of the tank main body
			fd30->density =2.0f;
			fd30->shape = new b2PolygonShape;
			fd30->shape = &shape20;
			body30->CreateFixture(fd30);
			body30->SetLinearVelocity(b2Vec2(-26.25f,0.0f));
			
			b2PolygonShape shape20yo;//shape20yo:b2PolygonShape, shape of the turret
			shape20yo.SetAsBox(15.0f, 1.0f);
			b2BodyDef bd30yo;/// bd30yo: b2BodyDef, Body Definition of the tank turret
			bd30yo.position.Set(-5.5f, 20.0f);
			bd30yo.type = b2_dynamicBody;
			b2Body* body30yo = m_world->CreateBody(&bd30yo);
			b2FixtureDef *fd30yo = new b2FixtureDef;///fd30yo: b2FixtureDef, Fixture of the tank turret
			fd30yo->density =0.02f;
			fd30yo->shape = new b2PolygonShape;
			fd30yo->shape = &shape20yo;
			body30yo->CreateFixture(fd30yo);
			
			b2WeldJointDef jdwl1;	//jdwl1:b2WeldJointDef, weld joint between turret and body
			jdwl1.Initialize(body30yo, body30,b2Vec2(-5.5f,22.0f));
			m_world->CreateJoint(&jdwl1);
			
			b2RevoluteJointDef jdwl;//jdwl:b2RevoluteJointDef, revolute joint between body and spiked wheel front
			b2Vec2 anchorwl;
			anchorwl.Set(2.0f, 10.0f);
			jdwl.Initialize(sbody2, body30, anchorwl);
			m_world->CreateJoint(&jdwl);
			
			b2RevoluteJointDef jdwr;//jdwr:b2RevoluteJointDef, revolute joint between body and spiked wheel rear
			b2Vec2 anchorwr;
			anchorwr.Set(30.0f, 10.0f);
			jdwr.Initialize(sbody, body30, anchorwr);
			m_world->CreateJoint(&jdwr);
			
			
			b2CircleShape circle2;
			circle2.m_radius = 3.3;
	
			b2FixtureDef ballfd40;
			ballfd40.shape = &circle2;
			ballfd40.density = 0.1f;
			ballfd40.friction = 0.0f;
			ballfd40.restitution = 0.0f;
			
	
			
			
			
      b2Body* sbodyf1;	// sbodyf1:b2Body pointer, creating the bottom wheels
	  b2BodyDef ballbdf1;// ballbd1: b2BodyDef, Body Definition for the bottom wheels
      ballbdf1.type = b2_dynamicBody;
      ballbdf1.position.Set(20.0f, 5.0f);
      sbodyf1 = m_world->CreateBody(&ballbdf1);            
      sbodyf1->CreateFixture(&ballfd40);
	  sbodyf1->SetLinearVelocity(b2Vec2(-26.25f,0));
	  sbodyf1->SetAngularVelocity(2.5f);
		
		b2Body* sbodyf2;// sbodyf2:b2Body pointer, creating the bottom wheels
	  b2BodyDef ballbdf2;// ballbd2: b2BodyDef, Body Definition for the bottom wheels
      ballbdf2.type = b2_dynamicBody;
      ballbdf2.position.Set(12.0f, 5.0f);
      sbodyf2 = m_world->CreateBody(&ballbdf2);
      sbodyf2->CreateFixture(&ballfd40);
	  sbodyf2->SetAngularVelocity(2.5f);
	  sbodyf2->SetLinearVelocity(b2Vec2(-26.25f,0));
	  
		b2RevoluteJointDef jdf1;//jdf1:b2RevoluteJointDef, Revolute joints between body and bottom wheel
		jdf1.Initialize(sbodyf1, body30, b2Vec2(20.0f,5.0f));
		m_world->CreateJoint(&jdf1);
			
		b2RevoluteJointDef jdf2;//jdf2:b2RevoluteJointDef, Revolute joints between body and bottom wheel
		jdf2.Initialize(sbodyf2, body30, b2Vec2(12.0f,5.0f));
		m_world->CreateJoint(&jdf2);
	}
		
  }

  sim_t *sim = new sim_t("Dominos", dominos_t::create);
}

