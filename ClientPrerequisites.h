
#ifndef __CLIENT_PREREQUISITES_H__
#define __CLIENT_PREREQUISITES_H__

#include "OgrePrerequisites.h"

#ifdef __PC_LINUX__   //linux服务器版本
	#define IWORLD_SERVER_BUILD    //是否是服务器开服版本
	#define IWORLD_TARGET_MOBILE   //目标是构建手机版
#else

	#if OGRE_PLATFORM==OGRE_PLATFORM_WIN32

		#define IWORLD_TARGET_PC   //目标是构建pc版
		//#define IWORLD_TARGET_MOBILE  //目标是构建手机版
		//#define IWORLD_TARGET_LIB    //构建盒子用的库而不是游戏

		#define IWORLD_REALTIME_SHADOW //实时阴影
		
		#define IWORLD_CROP_AR_IMAGE //裁剪玩家自选avatar图片
	#else

		#define IWORLD_TARGET_MOBILE  //目标是构建手机版
		#define IWORLD_REALTIME_SHADOW //实时阴影

	#endif

	//是否为开发状态，构建版本的时候要注释掉此行
#define IWORLD_DEV_BUILD

	#ifdef IWORLD_DEV_BUILD
	//是否需要导出场景到obj, 发行版注释
	//#define IWORLD_EXPOBJ_TOOLS
	#endif

#endif	 //__PC_LINUX__

#define  _EXP_GAMEAPI //导出游戏的开发接口
#define  USE_ACTOR_CONTROL

//#define IWORLD_ADVANCE_BUILD //先遣服

//#define IWORLD_ROBOT_TEST //机器人测试

#define RESOURCE_CENTER

//#define AR_DEMO			//ar动作demo版本

//Android编译开关，需修改mk文件

#endif   //__CLIENT_PREREQUISITES_H__

