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
      shape.Set(b2Vec2(-90.0f, 0.0f), b2Vec2(90.0f, 0.0f));
      b2BodyDef bd; 
      b1 = m_world->CreateBody(&bd); 
      b1->CreateFixture(&shape, 0.0f);
    }
    
	  b2Body* sbody;
      b2CircleShape circle;
      circle.m_radius = 5.0;
	
      b2FixtureDef ballfd;
      ballfd.shape = &circle;
      ballfd.density = 50.0f;
      ballfd.friction = 0.0f;
      ballfd.restitution = 0.0f;
      b2BodyDef ballbd;
      ballbd.type = b2_dynamicBody;
      ballbd.position.Set(30.0f, 10.0f);
      sbody = m_world->CreateBody(&ballbd);
      sbody->CreateFixture(&ballfd);
	  sbody->SetAngularVelocity(2.5f);
     
      b2Body* sbody2;
      
      b2BodyDef ballbd2;
      ballbd2.type = b2_dynamicBody;
      ballbd2.position.Set(2.0f, 10.0f);
      sbody2 = m_world->CreateBody(&ballbd2);
      sbody2->CreateFixture(&ballfd);
	  sbody2->SetAngularVelocity(2.5f);
     
     
     
      b2PolygonShape shape;
      shape.SetAsBox(1.0f, 0.02f);
      
      b2FixtureDef *fd2 = new b2FixtureDef;
      fd2->density = 1.f;
      fd2->shape = new b2PolygonShape;
      fd2->shape = &shape;
         
      for( int i=0; i<8 ; i++){
			b2BodyDef bd2;
			bd2.position.Set(30.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4));
			bd2.type = b2_dynamicBody;
			b2Body* body = m_world->CreateBody(&bd2);
			body->CreateFixture(fd2);
			body->SetTransform(b2Vec2(30.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4)), i*3.14/4);
			b2WeldJointDef jd;
			
			//b2Vec2 anchor;
			//anchor.Set(30.0f, 1.5f);
			jd.Initialize(sbody,body,b2Vec2(30.0f,10.0f));
			m_world->CreateJoint(&jd);
	
		}
	
	for( int i=0; i<8 ; i++){
			b2BodyDef bd2;
			bd2.position.Set(2.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4));
			bd2.type = b2_dynamicBody;
			b2Body* body = m_world->CreateBody(&bd2);
			body->CreateFixture(fd2);
			body->SetTransform(b2Vec2(2.0f + 5.0f*cos(i*3.14/4),10.0f+ 5.0f*sin(i*3.14/4)), i*3.14/4);
			b2WeldJointDef jd;
			
			//b2Vec2 anchor;
			//anchor.Set(30.0f, 1.5f);
			jd.Initialize(sbody2,body,b2Vec2(2.0f,10.0f));
			m_world->CreateJoint(&jd);
	
		}
	
		b2PolygonShape shape2;
      shape2.SetAsBox(1.3f , 0.5f);
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 0.1f;
      fd3->friction = 0.1f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape2;
      
      b2BodyDef bd3;
      b2Body* body[8];
      b2Body* body2[7];
      b2Body* body3[7];
      b2DistanceJointDef jd[8];
      b2DistanceJointDef jd2[8];
      b2DistanceJointDef jd3[8];
      /*
      bd3.position.Set(30.0f + 5.0f*cos(3.14/8), 10.0 + 6.0*sin(3.14/8));
		bd3.type = b2_dynamicBody;
		body[0] = m_world->CreateBody(&bd3);
		body[0]->CreateFixture(fd3);
		body[0]->SetTransform(b2Vec2(30.0f + 5.0f*cos( 3.14/8),10.0 + 6.0*sin(3.14/8)), 3.14/2);
      */
      for(int j=0; j<8; j++){
		if(j<2 || j>=6){
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
	  else{
			bd3.position.Set(2.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8));
			bd3.type = b2_dynamicBody;
			body[j] = m_world->CreateBody(&bd3);
			body[j]->CreateFixture(fd3);
			if(j==0 ||j==4 || j==3 || j==7){body[j]->SetTransform(b2Vec2(2.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)), 3.14/2);}
			
			if(j != 2 ){
				jd[j-1].Initialize(body[j-1],body[j],b2Vec2(2.0f + 5.0f*cos(((j-1)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((j-1)*3.14/4)+3.14/8)), b2Vec2(2.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)));
				m_world->CreateJoint(&jd[j-1]);
			}
		//if(j==7){ jd[7].Initialize(body[7],body[0],b2Vec2(30.0f + 5.0f*cos(((7)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((7)*3.14/4)+3.14/8)), b2Vec2(30.0f + 5.0f*cos((0*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((0*3.14/4)+3.14/8)));
			//	  m_world->CreateJoint(&jd[7]);
              //   }
		
		}
	  
	  }
	  
	  for(int i=0; i<7 ; i++ ){
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
	  
			jd2[0].Initialize(body[2], body2[0],b2Vec2(2.0f + 5.0f*cos(((2)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((2)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 0*3.9f, 15.0f));
					m_world->CreateJoint(&jd2[0]);
			jd2[7].Initialize(body[1], body2[6],b2Vec2(30.0f + 5.0f*cos(((1)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((1)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 6*3.9f, 15.0f));
					m_world->CreateJoint(&jd2[7]);
			jd3[0].Initialize(body[5], body3[0],b2Vec2(2.0f + 5.0f*cos(((5)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((5)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 0*3.9f, 5.0f));
					m_world->CreateJoint(&jd3[0]);
			jd3[7].Initialize(body[6], body3[6],b2Vec2(30.0f + 5.0f*cos(((6)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((6)*3.14/4)+3.14/8)), b2Vec2(2.0f+ 5.0f*cos((1*3.14/4)+ 3.14/8)+ 6*3.9f, 5.0f));
					m_world->CreateJoint(&jd3[7]);
			
					
			b2PolygonShape shape20;
			shape20.SetAsBox(20.0f, 10.0f);
			b2BodyDef bd30;
			bd30.position.Set(15.0f, 30.0f);
			bd30.type = b2_dynamicBody;
			b2Body* body30 = m_world->CreateBody(&bd30);
			b2FixtureDef *fd30 = new b2FixtureDef;
			fd30->density = 0.01f;
			fd30->shape = new b2PolygonShape;
			fd30->shape = &shape20;
			body30->CreateFixture(fd30);
			body30->SetLinearVelocity(b2Vec2(-10.5f,0.0f));
			
			b2RevoluteJointDef jdwl;
			b2Vec2 anchorwl;
			anchorwl.Set(2.0f, 10.0f);
			jdwl.Initialize(sbody2, body30, anchorwl);
			m_world->CreateJoint(&jdwl);
			
			b2RevoluteJointDef jdwr;
			b2Vec2 anchorwr;
			anchorwr.Set(30.0f, 10.0f);
			jdwr.Initialize(sbody, body30, anchorwr);
			m_world->CreateJoint(&jdwr);
			
			
			b2CircleShape circle2;
			circle2.m_radius = 3.5;
	
			b2FixtureDef ballfd40;
			ballfd40.shape = &circle2;
			ballfd40.density = 0.0f;
			ballfd40.friction = 0.0f;
			ballfd40.restitution = 0.0f;
      
      b2Body* sbodyf1;
	  b2BodyDef ballbdf1;
      ballbdf1.type = b2_dynamicBody;
      ballbdf1.position.Set(20.0f, 5.0f);
      sbodyf1 = m_world->CreateBody(&ballbdf1);
      sbodyf1->CreateFixture(&ballfd40);
		//sbodyf1->SetLinearVelocity(b2Vec2(2.5f,0));

	  //sbodyf1->SetAngularVelocity(2.5f);
		b2Body* sbodyf2;
	  b2BodyDef ballbdf2;
      ballbdf2.type = b2_dynamicBody;
      ballbdf2.position.Set(13.0f, 5.0f);
      sbodyf2 = m_world->CreateBody(&ballbdf2);
      sbodyf2->CreateFixture(&ballfd40);
	  //sbodyf2->SetAngularVelocity(2.5f);
	  
		b2WeldJointDef jdf1;
		jdf1.Initialize(sbodyf1, body30, b2Vec2(20.0f,5.0f));
		m_world->CreateJoint(&jdf1);
			
		b2WeldJointDef jdf2;
		jdf2.Initialize(sbodyf2, body30, b2Vec2(13.0f,5.0f));
		m_world->CreateJoint(&jdf2);
		
			 
	  //for(int i=0; i<3 ; i++){jd[j-1].Initialize(body[j-1],body[j],b2Vec2(30.0f + 5.0f*cos(((j-1)*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin(((j-1)*3.14/4)+3.14/8)), b2Vec2(30.0f + 5.0f*cos((j*3.14/4)+ 3.14/8),10.0f+ 6.0f*sin((j*3.14/4)+3.14/8)));
		//	m_world->CreateJoint(&jd[j-1]);
			//				}
	  
	  
	  /*
      b2BodyDef bd4;
      bd4.position.Set(31.0 + 3.6 + 0.2, 16.0f);
      bd4.type = b2_dynamicBody;
      b2Body* body4 = m_world->CreateBody(&bd4);
      body4->CreateFixture(fd3);
      
      b2BodyDef bd5;
      bd5.position.Set(31.0 + 3.6 +3.6 + 0.2, 16.0f);
      bd5.type = b2_dynamicBody;
      b2Body* body5 = m_world->CreateBody(&bd5);
      body5->CreateFixture(fd3);
      
      */
      
      
      //b2DistanceJointDef jd3;
      //jd3.Initialize(body4,body5,b2Vec2(31.0 + 3.6 + 0.2, 16.0f), b2Vec2(31.0 + 3.6 +3.6+ 0.2, 16.0f));
      //m_world->CreateJoint(&jd3);
      
      /*
      b2PolygonShape shape3;
      shape3.SetAsBox(1.4f , 0.2f);
      b2BodyDef bd4;
      bd4.position.Set(30.0f-j*(1.4f), 16.0f);
      bd4.type = b2_dynamicBody;
      b2Body* body4 = m_world->CreateBody(&bd4);
      b2FixtureDef *fd4 = new b2FixtureDef;
      fd4->density = 0.01f;
      fd4->shape = new b2PolygonShape;
      fd4->shape = &shape3;
      body4->CreateFixture(fd4);*/
  
      
      
      
		
   /*
      b2PolygonShape shape2;
      shape2.SetAsBox(20.0f, 20.0f);
      b2BodyDef bd3;
      bd3.position.Set(25.0f, 22.0f);
      bd3.type = b2_dynamicBody;
      b2Body* body3 = m_world->CreateBody(&bd3);
      b2FixtureDef *fd3 = new b2FixtureDef;
      fd3->density = 100.01f;
      fd3->shape = new b2PolygonShape;
      fd3->shape = &shape2;
      body3->CreateFixture(fd3);
    
     //b2Vec2 worldaxis;
     //worldaxis.Set(13.0f, 22.0f);
      b2WheelJointDef jd2;
      jd2.collideConnected = true;
      jd2.dampingRatio = 0.7f;
      jd2.frequencyHz= 4.0f;
      jd2.enableMotor = true;
      jd2.motorSpeed=10.0f;
      jd2.maxMotorTorque = 100.0f;
      
      b2Vec2 anchor;
      anchor.Set(30.0f,22.0f);
      jd2.Initialize( body3,sbody,b2Vec2(30.0f, 22.0f),b2Vec2(10.0f,22.0f));
      
      m_world->CreateJoint(&jd2);
    */
    /*      
    //Top horizontal shelf
    {
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
    }


    //The see-saw system at the bottom
    {
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
