
#ifndef __CLIENT_PREREQUISITES_H__
#define __CLIENT_PREREQUISITES_H__

#include "OgrePrerequisites.h"

#ifdef __PC_LINUX__   //linux�������汾
	#define IWORLD_SERVER_BUILD    //�Ƿ��Ƿ����������汾
	#define IWORLD_TARGET_MOBILE   //Ŀ���ǹ����ֻ���
#else

	#if OGRE_PLATFORM==OGRE_PLATFORM_WIN32

		#define IWORLD_TARGET_PC   //Ŀ���ǹ���pc��
		//#define IWORLD_TARGET_MOBILE  //Ŀ���ǹ����ֻ���
		//#define IWORLD_TARGET_LIB    //���������õĿ��������Ϸ

		#define IWORLD_REALTIME_SHADOW //ʵʱ��Ӱ
		
		#define IWORLD_CROP_AR_IMAGE //�ü������ѡavatarͼƬ
	#else

		#define IWORLD_TARGET_MOBILE  //Ŀ���ǹ����ֻ���
		#define IWORLD_REALTIME_SHADOW //ʵʱ��Ӱ

	#endif

	//�Ƿ�Ϊ����״̬�������汾��ʱ��Ҫע�͵�����
//#define IWORLD_DEV_BUILD

	#ifdef IWORLD_DEV_BUILD
	//�Ƿ���Ҫ����������obj, ���а�ע��
	//#define IWORLD_EXPOBJ_TOOLS
	#endif

#endif	 //__PC_LINUX__

#define  _EXP_GAMEAPI //������Ϸ�Ŀ����ӿ�
#define  USE_ACTOR_CONTROL

//#define IWORLD_ADVANCE_BUILD //��ǲ��

//#define IWORLD_ROBOT_TEST //�����˲���

#define RESOURCE_CENTER

//#define AR_DEMO			//ar����demo�汾

//Android���뿪�أ����޸�mk�ļ�

#endif   //__CLIENT_PREREQUISITES_H__

