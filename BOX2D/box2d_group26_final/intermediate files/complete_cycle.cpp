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
    
      /*b2BodyDef myBodyDef;
	  myBodyDef.type = b2_dynamicBody; //this will be a dynamic body
	  myBodyDef.position.Set(0, 20); //set the starting position
	  myBodyDef.angle = 0; //set the starting angle
	  
	  b2Body* dynamicBody = m_world->CreateBody(&myBodyDef);
	  
	  b2PolygonShape boxShape;
	  boxShape.SetAsBox(1,2);
	  
	  b2FixtureDef boxFixtureDef;
	  boxFixtureDef.shape = &boxShape;
	  boxFixtureDef.density = 1;
	  dynamicBody->CreateFixture(&boxFixtureDef);
	  
	  dynamicBody->SetLinearVelocity( b2Vec2( -5, 5 ) ); //moving up and left 5 units per second
	  dynamicBody->SetAngularVelocity( -90 * DEGTORAD ); //90 degrees per second clockwise
      
      
      myBodyDef.type = b2_staticBody; //this will be a static body
	  myBodyDef.position.Set(0, 10); //slightly lower position
	  b2Body* staticBody = m_world->CreateBody(&myBodyDef); //add body to world
	  staticBody->CreateFixture(&boxFixtureDef); //add fixture to body
	  
	  myBodyDef.type = b2_kinematicBody; //this will be a kinematic body
	  myBodyDef.position.Set(-18, 7); // start from left side, slightly above the static body
	  b2Body* kinematicBody = m_world->CreateBody(&myBodyDef); //add body to world
	  kinematicBody->CreateFixture(&boxFixtureDef); //add fixture to body
	  kinematicBody->SetLinearVelocity( b2Vec2( 15, 0 ) ); //move right 1 unit per second
	  kinematicBody->SetAngularVelocity( 1000 * DEGTORAD ); //1 turn per second counter-clockwise
	  
	  myBodyDef.type = b2_kinematicBody; //this will be a kinematic body
	  myBodyDef.position.Set(-18, 10); // start from left side, slightly above the static body
	  b2Body* kinematicBody2 = m_world->CreateBody(&myBodyDef); //add body to world
	  kinematicBody2->CreateFixture(&boxFixtureDef); //add fixture to body
	  kinematicBody2->SetLinearVelocity( b2Vec2( 15, 0 ) ); //move right 1 unit per second
	  kinematicBody2->SetAngularVelocity( 1000 * DEGTORAD ); //1 turn per second counter-clockwise
    */
    
    //Top horizontal shelf
    /*{
      b2PolygonShape shape;
      shape.SetAsBox(6.0f, 0.25f);
	
      b2BodyDef bd;
      bd.position.Set(-31.0f, 30.0f);
      b2Body* ground = m_world->CreateBody(&bd);
      ground->CreateFixture(&shape, 0.0f);
    }

    //Dominos
    {
      b2PolygonShape shape;
      shape.SetAsBox(0.1f, 1.0f);
	
      b2FixtureDef fd;
      fd.shape = &shape;
      fd.density = 20.0f;
      fd.friction = 0.1f;
		
      for (int i = 0; i < 10; ++i)
	{
	  b2BodyDef bd;
	  bd.type = b2_dynamicBody;
	  bd.position.Set(-35.5f + 1.0f * i, 31.25f);
	  b2Body* body = m_world->CreateBody(&bd);
	  body->CreateFixture(&fd);
	}
    }
      
    //Another horizontal shelf
    {
      b2PolygonShape shape;
      shape.SetAsBox(7.0f, 0.25f, b2Vec2(-20.f,20.f), 0.0f);
	
      b2BodyDef bd;
      bd.position.Set(1.0f, 6.0f);
      b2Body* ground = m_world->CreateBody(&bd);
      ground->CreateFixture(&shape, 0.0f);
    }


    //The pendulum that knocks the dominos off
    {
      b2Body* b2;
      {
	b2PolygonShape shape;
	shape.SetAsBox(0.25f, 1.5f);
	  
	b2BodyDef bd;
	bd.position.Set(-36.5f, 28.0f);
	b2 = m_world->CreateBody(&bd);
	b2->CreateFixture(&shape, 10.0f);
      }
	
      b2Body* b4;
      {
	b2PolygonShape shape;
	shape.SetAsBox(0.25f, 0.25f);
	  
	b2BodyDef bd;
	bd.type = b2_dynamicBody;
	bd.position.Set(-40.0f, 33.0f);
	b4 = m_world->CreateBody(&bd);
	b4->CreateFixture(&shape, 2.0f);
      }
	
      b2RevoluteJointDef jd;
      b2Vec2 anchor;
      anchor.Set(-37.0f, 40.0f);
      jd.Initialize(b2, b4, anchor);
      m_world->CreateJoint(&jd);
    }
      
    //The train of small spheres
    {
      b2Body* spherebody;
	
      b2CircleShape circle;
      circle.m_radius = 0.5;
	
      b2FixtureDef ballfd;
      ballfd.shape = &circle;
      ballfd.density = 1.0f;
      ballfd.friction = 0.0f;
      ballfd.restitution = 0.0f;
	
      for (int i = 0; i < 10; ++i)
	{
	  b2BodyDef ballbd;
	  ballbd.type = b2_dynamicBody;
	  ballbd.position.Set(-22.2f + i*1.0, 26.6f);
	  spherebody = m_world->CreateBody(&ballbd);
	  spherebody->CreateFixture(&ballfd);
	}
    }

    //The pulley system
    {
      b2BodyDef *bd = new b2BodyDef;
      bd->type = b2_dynamicBody;
      bd->position.Set(-10,15);
      bd->fixedRotation = true;
      
      //The open box
      b2FixtureDef *fd1 = new b2FixtureDef;
      fd1->density = 10.0;
      fd1->friction = 0.5;
      fd1->restitution = 0.f;
      fd1->shape = new b2PolygonShape;
      b2PolygonShape bs1;
      bs1.SetAsBox(2,0.2, b2Vec2(0.f,-1.9f), 0);
      fd1->shape = &bs1;
      b2FixtureDef *fd2 = new b2FixtureDef;
      fd2->density = 10.0;
      fd2->friction = 0.5;
      fd2->restitution = 0.f;
      fd2->shape = new b2PolygonShape;
      b2PolygonShape bs2;
      bs2.SetAsBox(0.2,2, b2Vec2(2.0f,0.f), 0);
      fd2->shape = &bs2;
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 10.0;
      fd3->friction = 0.5;
      fd3->restitution = 0.f;
      fd3->shape = new b2PolygonShape;
      b2PolygonShape bs3;
      bs3.SetAsBox(0.2,2, b2Vec2(-2.0f,0.f), 0);
      fd3->shape = &bs3;
       
      b2Body* box1 = m_world->CreateBody(bd);
      box1->CreateFixture(fd1);
      box1->CreateFixture(fd2);
      box1->CreateFixture(fd3);

      //The bar
      bd->position.Set(10,15);	
      fd1->density = 34.0;	  
      b2Body* box2 = m_world->CreateBody(bd);
      box2->CreateFixture(fd1);

      // The pulley joint
      b2PulleyJointDef* myjoint = new b2PulleyJointDef();
      b2Vec2 worldAnchorOnBody1(-10, 15); // Anchor point on body 1 in world axis
      b2Vec2 worldAnchorOnBody2(10, 15); // Anchor point on body 2 in world axis
      b2Vec2 worldAnchorGround1(-10, 20); // Anchor point for ground 1 in world axis
      b2Vec2 worldAnchorGround2(10, 20); // Anchor point for ground 2 in world axis
      float32 ratio = 1.0f; // Define ratio
      myjoint->Initialize(box1, box2, worldAnchorGround1, worldAnchorGround2, box1->GetWorldCenter(), box2->GetWorldCenter(), ratio);
      m_world->CreateJoint(myjoint);
    }

    //The revolving horizontal platform
    {
      b2PolygonShape shape;
      shape.SetAsBox(2.2f, 0.2f);
	
      b2BodyDef bd;
      bd.position.Set(14.0f, 14.0f);
      bd.type = b2_dynamicBody;
      b2Body* body = m_world->CreateBody(&bd);
      b2FixtureDef *fd = new b2FixtureDef;
      fd->density = 1.f;
      fd->shape = new b2PolygonShape;
      fd->shape = &shape;
      body->CreateFixture(fd);

      b2PolygonShape shape2;
      shape2.SetAsBox(0.2f, 2.0f);
      b2BodyDef bd2;
      bd2.position.Set(14.0f, 16.0f);
      b2Body* body2 = m_world->CreateBody(&bd2);

      b2RevoluteJointDef jointDef;
      jointDef.bodyA = body;
      jointDef.bodyB = body2;
      jointDef.localAnchorA.Set(0,0);
      jointDef.localAnchorB.Set(0,0);
      jointDef.collideConnected = false;
      m_world->CreateJoint(&jointDef);
    }

    //The heavy sphere on the platform
    {
      b2Body* sbody;
      b2CircleShape circle;
      circle.m_radius = 1.0;
	
      b2FixtureDef ballfd;
      ballfd.shape = &circle;
      ballfd.density = 50.0f;
      ballfd.friction = 0.0f;
      ballfd.restitution = 0.0f;
      b2BodyDef ballbd;
      ballbd.type = b2_dynamicBody;
      ballbd.position.Set(14.0f, 18.0f);
      sbody = m_world->CreateBody(&ballbd);
      sbody->CreateFixture(&ballfd);
    }*/

	//present working
	float xpos=45.0 , ypos=38.0;
	{
		//front wheel
		b2Body* sbody;
		  b2CircleShape circle;
      circle.m_radius = 3.0;
	
      b2FixtureDef ballfd;
      ballfd.shape = &circle;
      ballfd.density = 0.8f;
      ballfd.friction = 0.5f;
      ballfd.restitution = 0.0f;
      b2BodyDef ballbd;
      ballbd.type = b2_dynamicBody;
      ballbd.position.Set(xpos, ypos);
      sbody = m_world->CreateBody(&ballbd);
      sbody->CreateFixture(&ballfd);
      //sbody->SetLinearVelocity(b2Vec2(-15,0));
      //sbody->SetAngularVelocity(55/7);
  
      
      //rear wheel
      b2Body* sbody1;
      b2CircleShape circle1;
      circle1.m_radius = 3.0;
	
      b2FixtureDef ballfd1;
      ballfd1.shape = &circle1;
      ballfd1.density = 0.8f;
      ballfd1.friction = 0.5f;
      ballfd1.restitution = 0.0f;
      b2BodyDef ballbd1;
      ballbd1.type = b2_dynamicBody;
      ballbd1.position.Set(xpos+10.0, ypos);
      sbody1 = m_world->CreateBody(&ballbd1);
      sbody1->CreateFixture(&ballfd1);
      //sbody1->SetLinearVelocity(b2Vec2(-5,0));
      //sbody1->SetAngularVelocity(55/7);
	
	
	//pedal
	 b2Body* sbody2;
      b2CircleShape circle2;
      circle2.m_radius = 1.0;
	
      b2FixtureDef ballfd2;
      ballfd2.shape = &circle2;
      ballfd2.density = 0.5f;
      ballfd2.friction = 1.0f;
      ballfd2.restitution = 0.0f;
      b2BodyDef ballbd2;
      ballbd2.type = b2_dynamicBody;
      ballbd2.position.Set(xpos+5, ypos+2);
      sbody2 = m_world->CreateBody(&ballbd2);
      sbody2->CreateFixture(&ballfd2);
      //sbody2->SetLinearVelocity(b2Vec2(-5,0));
      //sbody2->SetAngularVelocity(55/7);
	
		/*
		// joint between tires
		{b2RevoluteJointDef jd;
      b2Vec2 anchor;
      anchor.Set(xpos+2.5, ypos);
      jd.Initialize(sbody1, sbody2, anchor);
      m_world->CreateJoint(&jd);
	}
		*/
		
		
		//cycle seat
		b2Body* b1;
      
	b2PolygonShape shape1;
	shape1.SetAsBox(0.8f, 0.25f);//length width
	b2BodyDef bd1;
	bd1.type = b2_dynamicBody;
	bd1.position.Set(xpos+7.0, ypos+5.0);
	b1 = m_world->CreateBody(&bd1);
	b1->CreateFixture(&shape1, 2.0f);

	//handle
	b2Body* b2;
	b2PolygonShape shape2;
	shape2.SetAsBox(1.5f, 0.25f);//length width
	b2BodyDef bd2;
	bd2.type = b2_dynamicBody;
	bd2.position.Set(xpos+2.5, ypos+5.5);
	b2 = m_world->CreateBody(&bd2);
	b2->CreateFixture(&shape2, 2.0f);
	
	
	//seat to pedal
	b2WeldJointDef weldJointDef1;
    weldJointDef1.bodyA = sbody2;
    weldJointDef1.bodyB = b1;

    weldJointDef1.localAnchorA = weldJointDef1.bodyA->GetLocalPoint(b2Vec2(xpos+5,ypos+2));
    weldJointDef1.localAnchorB = weldJointDef1.bodyB->GetLocalPoint(b2Vec2(xpos+7,ypos+5));
    weldJointDef1.collideConnected = true;
    weldJointDef1.Initialize(sbody2, b1, b2Vec2(xpos+5,ypos+2));
    m_world->CreateJoint(&weldJointDef1);	
		
	
	//pedal to handle
	b2WeldJointDef weldJointDef2;
    weldJointDef2.bodyA = sbody2;
    weldJointDef2.bodyB = b2;

    weldJointDef2.localAnchorA = weldJointDef1.bodyA->GetLocalPoint(b2Vec2(xpos+5,ypos+2));
    weldJointDef2.localAnchorB = weldJointDef1.bodyB->GetLocalPoint(b2Vec2(xpos+2.5,ypos+5.5));
    weldJointDef2.collideConnected = true;
    weldJointDef2.Initialize(sbody2, b2, b2Vec2(xpos+5,ypos+2));
    m_world->CreateJoint(&weldJointDef2);
	
	//handle to seat
	b2DistanceJointDef jointDef5;	
	jointDef5.Initialize(b1, b2, b2Vec2(xpos+7,ypos+5), b2Vec2(xpos+2.5,ypos+5.5));
	jointDef5.collideConnected = true;
	m_world->CreateJoint(&jointDef5);
		
	//pedal to rear tire
	b2DistanceJointDef jointDef6;	
	jointDef6.Initialize(sbody2, sbody1, b2Vec2(xpos+5,ypos+2), b2Vec2(xpos+10,ypos));
	jointDef6.collideConnected = true;
	m_world->CreateJoint(&jointDef6);
	
	//pedal to front tire
	b2DistanceJointDef jointDef7;	
	jointDef7.Initialize(sbody2, sbody, b2Vec2(xpos+5,ypos+2), b2Vec2(xpos,ypos));
	jointDef7.collideConnected = true;
	m_world->CreateJoint(&jointDef7);
	
	//handle to rear wheel
	b2DistanceJointDef jointDef3;	
	jointDef3.Initialize(sbody1, b2, b2Vec2(xpos+10,ypos), b2Vec2(xpos+2.5,ypos+5.5));
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
	b2DistanceJointDef jointDef1;	
	jointDef1.Initialize(sbody, b2, b2Vec2(xpos,ypos), b2Vec2(xpos+2.5,ypos+5.5));
	jointDef1.collideConnected = true;
	jointDef1.frequencyHz = 1.0f;
	jointDef1.dampingRatio = 1.4f;
	m_world->CreateJoint(&jointDef1);

	//back suspension( rear wheel to seat)
	b2DistanceJointDef jointDef2;	
	jointDef2.Initialize(sbody1, b1, b2Vec2(xpos + 10,ypos), b2Vec2(xpos+7,ypos+5.0));
	jointDef2.collideConnected = true;
	jointDef2.frequencyHz = 1.5f;
	jointDef2.dampingRatio = 1.4f;
	m_world->CreateJoint(&jointDef2);

	
	//b2RevoluteJointDef jd2;//joint 3
      //jd2.Initialize(sbody, b40, anchor1);
      //m_world->CreateJoint(&jd2);
      
      
      // starting plank
      b2PolygonShape shape3; 
      shape3.SetAsBox(28.0f, 0.2f);
      b2BodyDef bd3;
      bd3.position.Set(35.0f, 15.0f);
      bd3.angle = -2.4;
      //bd2.type = b2_dynamicBody;
      b2Body* body3 = m_world->CreateBody(&bd3);
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 1.f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape3;
      body3->CreateFixture(fd3);
  
      
      //second plank
      {b2PolygonShape shape4; 
            shape4.SetAsBox(10.0f, 0.2f);
      b2BodyDef bd4;
      bd4.position.Set(-10.0f, -0.5f);
      bd4.angle = 2.8;
      //bd2.type = b2_dynamicBody;
      b2Body* body4 = m_world->CreateBody(&bd4);
      b2FixtureDef *fd4 = new b2FixtureDef;
      fd4->density = 1.f;
      fd4->shape = new b2PolygonShape;
      fd4->shape = &shape4;
      body4->CreateFixture(fd4);
  }
      
      //boxes
      {for(int q=0; q<48;q++){
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
      body3->CreateFixture(fd3);}
  }
      
    /*  
	b2Body* b20;//useless joint 1
      {
	b2PolygonShape shape1;
	shape1.SetAsBox(0.01f, 0.01f);//length width
	  
	b2BodyDef bd12;
	bd12.type = b2_dynamicBody;
	bd12.position.Set(2.5f, 0.0f);
	b20 = m_world->CreateBody(&bd12);
	b20->CreateFixture(&shape1, 2.0f);
}
	b2Body* b30;//useless joint 2
      {
	b2PolygonShape shape2;
	shape2.SetAsBox(0.01f, 0.01f);//length width
	  
	b2BodyDef bd13;
	bd13.type = b2_dynamicBody;
	bd13.position.Set(1.25f, 5.0f);
	b30 = m_world->CreateBody(&bd13);
	b30->CreateFixture(&shape2, 2.0f);
}
	b2Vec2 anchor2;
      anchor2.Set(5.0f, 0.0f);
     
     b2Vec2 anchor3;
      anchor3.Set(2.5f, 0.0f);
      
     b2Vec2 anchor4;
      anchor4.Set(5.0f, 10.0f);  
	
	b2RevoluteJointDef jd3;//joint 4
      jd3.Initialize(b20, b30, anchor3);
      m_world->CreateJoint(&jd3);
    
    b2RevoluteJointDef jd4;//joint 5
      jd4.Initialize(b20, sbody, anchor1);
      m_world->CreateJoint(&jd4);
      
      b2RevoluteJointDef jd5;//joint 3
      jd5.Initialize(b20, sbody1, anchor2);
      m_world->CreateJoint(&jd5);
      
      b2RevoluteJointDef jd6;//joint 3
      jd6.Initialize(b30, sbody1, anchor2);
      m_world->CreateJoint(&jd6);
     
     b2RevoluteJointDef jd7;//joint 3
      jd7.Initialize(b30, b40, anchor4);
      m_world->CreateJoint(&jd7);
    */
      
		
		
		}
	
    //The see-saw system at the bottom
   /* {
      //The triangle wedge
      b2Body* sbody;
      b2PolygonShape poly;
      b2Vec2 vertices[3];
      vertices[0].Set(-1,0);
      vertices[1].Set(1,0);
      vertices[2].Set(0,1.5);
      poly.Set(vertices, 3);
      b2FixtureDef wedgefd;
      wedgefd.shape = &poly;
      wedgefd.density = 10.0f;
      wedgefd.friction = 0.0f;
      wedgefd.restitution = 0.0f;
      b2BodyDef wedgebd;
      wedgebd.position.Set(30.0f, 0.0f);
      sbody = m_world->CreateBody(&wedgebd);
      sbody->CreateFixture(&wedgefd);

      //The plank on top of the wedge
      b2PolygonShape shape;
      shape.SetAsBox(15.0f, 0.2f);
      b2BodyDef bd2;
      bd2.position.Set(30.0f, 1.5f);
      bd2.type = b2_dynamicBody;
      b2Body* body = m_world->CreateBody(&bd2);
      b2FixtureDef *fd2 = new b2FixtureDef;
      fd2->density = 1.f;
      fd2->shape = new b2PolygonShape;
      fd2->shape = &shape;
      body->CreateFixture(fd2);

      b2RevoluteJointDef jd;
      b2Vec2 anchor;
      anchor.Set(30.0f, 1.5f);
      jd.Initialize(sbody, body, anchor);
      m_world->CreateJoint(&jd);

      //The light box on the right side of the see-saw
      b2PolygonShape shape2;
      shape2.SetAsBox(2.0f, 2.0f);
      b2BodyDef bd3;
      bd3.position.Set(40.0f, 2.0f);
      bd3.type = b2_dynamicBody;
         
      b2Body* body3 = m_world->CreateBody(&bd3);
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 0.01f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape2;
      body3->CreateFixture(fd3);
    }*/
  }

  sim_t *sim = new sim_t("Dominos", dominos_t::create);
}
