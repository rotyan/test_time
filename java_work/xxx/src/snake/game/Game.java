package snake.game;

import java.awt.EventQueue;

import snake.view.MainWindow;
/*
 * 作为游戏的主方法，启动游戏，通过启动窗体，实现启动程序
 */
public class Game {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

/*游戏框架分析：
1：Snake，Food，Ground：这是三个类，每个类里面完成各自的方法。如，蛇有蛇初始化，蛇吃食物，蛇运动等方法。食物类有判断蛇是否吃到食物，尝试食物的方法。石头类有产生石头，判断蛇是否撞到石头等方法。这是三个实体类。
2：Controller控制类：所有控制游戏，以及逻辑上的方法在这里面实现。比如，控制蛇的方向，开始新游戏等方法。
3：SnakeListener蛇监听类：一个接口，用来监听蛇是否运动。
4：GamePanel游戏界面类：用来显示游戏的画面。
5：Global全局类：用来存放全局变量，如游戏界面的宽度，高度。
6：MainWindow类：这是游戏的主窗体。各种按键事件在这里面实现。
7：Game类：游戏的主方法，用来开始程序。
参考文献：https://cshihong.github.io/2017/09/03/Java%E7%BC%96%E7%A8%8B-%E8%B4%AA%E5%90%83%E8%9B%87%E6%B8%B8%E6%88%8F/
*/
