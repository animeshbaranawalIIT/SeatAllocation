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
#include <iostream>


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
    b2Body* b1;  
    {
      
      b2EdgeShape shape; 
      shape.Set(b2Vec2(-120.0f,-4.0f), b2Vec2(120.0f, -4.0f));
      b2BodyDef bd; 
      b1 = m_world->CreateBody(&bd); 
      b1->CreateFixture(&shape, 0.0f);
    }
    /////////////////////////////////////////////////////////////////////
    //CAR
      b2BodyDef carbd;
	  b2FixtureDef carfd;
	  	      
	  carbd.type = b2_dynamicBody; //this will be a dynamic body
	  carbd.angle = 0; //set the starting angle
	  carfd.density = 10; //set the starting angle
	  
	  int x = 55,y = 40;
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
	   wheel1->SetLinearVelocity(b2Vec2(-5,0));
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
	   wheel2->SetLinearVelocity(b2Vec2(-5,0));
       wheel2->SetAngularVelocity(11/7);
	   wheel2->CreateFixture(&wheelfd2);
      
      b2DistanceJointDef joint1;
      joint1.Initialize(car,wheel1,b2Vec2(x-1.5,y-7.5),b2Vec2(x-1.5,y-7.5));
      joint1.collideConnected = true;
      m_world->CreateJoint(&joint1);
      
      b2DistanceJointDef joint2;
      joint2.Initialize(car,wheel2,b2Vec2(x-8.5,y-7.5),b2Vec2(x-8.5,y-7.5));
      joint2.collideConnected = true;
      m_world->CreateJoint(&joint2);
      
	  
	/////////////////////////////////////////////////////////////////////////////  
	  //~ b2PolygonShape boxShape;
	  //~ boxShape.SetAsBox(1,2);
	  //~ 
	  //~ b2FixtureDef boxFixtureDef;
	  //~ boxFixtureDef.shape = &boxShape;
	  //~ boxFixtureDef.density = 1;
	  //~ dynamicBody->CreateFixture(&boxFixtureDef);
	  //~ 
	  //~ dynamicBody->SetLinearVelocity( b2Vec2( -5, 5 ) ); //moving up and left 5 units per second
	  //~ dynamicBody->SetAngularVelocity( -90 * DEGTORAD ); //90 degrees per second clockwise
      
     /////////////////////////////////////////////////////////////////////////// 
     b2PolygonShape boxShape;
     boxShape.SetAsBox(1,2);// 2*4 rectangle
      b2BodyDef myBodyDef;
      b2FixtureDef boxFixtureDef;
      boxFixtureDef.shape = &boxShape;
	  boxFixtureDef.density = 1;
      	  
	  myBodyDef.type = b2_kinematicBody; //this will be a kinematic body
	  myBodyDef.position.Set(-18, 40); // start from left side, slightly above the static body
	  b2Body* kinematicBody = m_world->CreateBody(&myBodyDef); //add body to world
	  	   
	  kinematicBody->CreateFixture(&boxFixtureDef); //add fixture to body
	  kinematicBody->SetLinearVelocity( b2Vec2( 15, 0 ) ); //move right 1 unit per second
	  kinematicBody->SetAngularVelocity( 1000 * DEGTORAD ); //1 turn per second counter-clockwise
	  
	  
	  myBodyDef.type = b2_kinematicBody; //this will be a kinematic body
	  myBodyDef.position.Set(-18, 50); // start from left side, slightly above the static body
	  b2Body* kinematicBody2 = m_world->CreateBody(&myBodyDef); //add body to world
	  kinematicBody2->CreateFixture(&boxFixtureDef); //add fixture to body
	  kinematicBody2->SetLinearVelocity( b2Vec2( 15, 0 ) ); //move right 1 unit per second
	  kinematicBody2->SetAngularVelocity( 1000 * DEGTORAD ); //1 turn per second counter-clockwise
	  
    

	//present working
	float xpos=-40.0 , ypos=38.0;
	{
		b2Body* sbody;//wheel 1
      b2CircleShape circle;
      circle.m_radius = 3.0;
	
      b2FixtureDef ballfd;
      ballfd.shape = &circle;
      ballfd.density = 1.0f;
      ballfd.friction = 0.0f;
      ballfd.restitution = 0.0f;
      b2BodyDef ballbd;
      ballbd.type = b2_dynamicBody;
      ballbd.position.Set(xpos, ypos);
      sbody = m_world->CreateBody(&ballbd);
      sbody->CreateFixture(&ballfd);
      sbody->SetLinearVelocity(b2Vec2(-15,0));
      sbody->SetAngularVelocity(11/7);
      
      b2Body* sbody1;//second wheel
      b2CircleShape circle1;
      circle1.m_radius = 2.5;
	
      b2FixtureDef ballfd1;
      ballfd1.shape = &circle1;
      ballfd1.density = 1.0f;
      ballfd1.friction = 1.0f;
      ballfd1.restitution = 0.0f;
      b2BodyDef ballbd1;
      ballbd1.type = b2_dynamicBody;
      ballbd1.position.Set(xpos+10.0, ypos);
      sbody1 = m_world->CreateBody(&ballbd1);
      sbody1->CreateFixture(&ballfd1);
      sbody1->SetLinearVelocity(b2Vec2(-5,0));
      sbody1->SetAngularVelocity(55/7);
		
		b2RevoluteJointDef jd;//joint1
      b2Vec2 anchor;
      anchor.Set(xpos+10.0, ypos);
      jd.Initialize(sbody1, sbody, anchor);
      m_world->CreateJoint(&jd);
		
		b2Body* b40;//car body
      {
	b2PolygonShape shape;
	shape.SetAsBox(2.0f, 0.25f);//length width
	  
	b2BodyDef bd1;
	bd1.type = b2_dynamicBody;
	bd1.position.Set(xpos+2.5, ypos+5.0);
	b40 = m_world->CreateBody(&bd1);
	b40->CreateFixture(&shape, 2.0f);
	
	b2RevoluteJointDef jd1;//joint 2
      b2Vec2 anchor1;
      anchor1.Set(xpos, ypos);
      jd1.Initialize(sbody1,b40, anchor);
      m_world->CreateJoint(&jd1);
	
	b2RevoluteJointDef jd2;//joint 3
      jd2.Initialize(sbody, b40, anchor1);
      m_world->CreateJoint(&jd2);
      }
      
      b2PolygonShape shape; // starting plank
      shape.SetAsBox(28.0f, 0.2f);
      b2BodyDef bd2;
      bd2.position.Set(35.0f, 15.0f);
      bd2.angle = -2.4;
      //bd2.type = b2_dynamicBody;
      b2Body* body = m_world->CreateBody(&bd2);
      b2FixtureDef *fd2 = new b2FixtureDef;
      fd2->density = 1.f;
      fd2->shape = new b2PolygonShape;
      fd2->shape = &shape;
      body->CreateFixture(fd2);
      
      b2PolygonShape shape1; // starting plank
      shape1.SetAsBox(10.0f, 0.2f);
      b2BodyDef bd3;
      bd3.position.Set(-10.0f, -0.5f);
      bd3.angle = 2.8;
      //bd2.type = b2_dynamicBody;
      b2Body* body1 = m_world->CreateBody(&bd3);
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 1.f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape1;
      body1->CreateFixture(fd3);
      
      for(int q=0; q<48;q++){
      b2PolygonShape shape2;
      float side;side = 2;
      shape2.SetAsBox(side, side);
      b2BodyDef bd3;
      bd3.position.Set(-40.0f+ 2*(side)*(q%4), 2*side*(q/4)-2);
      bd3.type = b2_dynamicBody;
         
      b2Body* body3 = m_world->CreateBody(&bd3);
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 0.01f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape2;
      body3->CreateFixture(fd3);
      body3->SetGravityScale(0);//no gravity!!
      }
    
  }
  }

  sim_t *sim = new sim_t("Dominos", dominos_t::create);
}

