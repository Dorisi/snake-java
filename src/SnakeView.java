import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class SnakeView implements Observer {
    SnakeControl control = null;
    SnakeModel model = null;

    JFrame mainFrame;
    Canvas paintCanvas;
    JLabel labelScore;

    public static final int canvasWidth = 200;//画布的宽度
    public static final int canvasHeight = 300;//画布的高度
    //$$$$$
    //public static final int canvasWidth = 400;//画布的宽度
    //public static final int canvasHeight = 600;//画布的高度
    public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;
    //$$$$
    //public static final int nodeWidth = 20;
    //public static final int nodeHeight = 20;

    public SnakeView(SnakeModel model, SnakeControl control) {
        this.model = model;
        this.control = control;

        //mainFrame = new JFrame("GreedSnake");
        mainFrame = new JFrame("你以为我只是一条蛇？");//$$$$$

        Container cp = mainFrame.getContentPane();

        // 创建顶部的分数显示
        labelScore = new JLabel("Score:");
        cp.add(labelScore, BorderLayout.NORTH);

        // 创建中间的游戏显示区域
        paintCanvas = new Canvas();// 创建一个空的画布
        paintCanvas.setSize(canvasWidth + 1, canvasHeight + 1);
        paintCanvas.addKeyListener(control);
        cp.add(paintCanvas, BorderLayout.CENTER);

        // 创建底下的帮助栏
        JPanel panelButtom = new JPanel();
        panelButtom.setLayout(new BorderLayout());
        JLabel labelHelp;
        labelHelp = new JLabel("PageUp, PageDown for speed;", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel("ENTER or R or S for start;", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.CENTER);
        labelHelp = new JLabel("SPACE or P for pause", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.SOUTH);
        cp.add(panelButtom, BorderLayout.SOUTH);

        mainFrame.addKeyListener(control);//键盘监听
        mainFrame.pack();//调整此窗口的大小，以适合其子组件的首选大小和布局。
        mainFrame.setResizable(false);//用户不可以自由改变该窗体的大小
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭时退出程序
        mainFrame.setVisible(true);//设置可见
    }
//当被观察者model发出通知时更新
    void repaint() {
        Graphics g = paintCanvas.getGraphics();//得到一个Graphics类的对象

        //draw background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        // draw the snake
        g.setColor(Color.BLACK);
        LinkedList na = model.nodeArray;//双向列表,列表中的每个节点都包含了对前一个和后一个元素的引用.
        //model.nodeArray 就是蛇体
        Iterator it = na.iterator();//遍历并选择序列中的对象
        while (it.hasNext()) {//使用hasNext()检查序列中是否还有元素。
            Node n = (Node) it.next();
            drawNode(g, n);
        }

        // draw the food
        g.setColor(Color.RED);
        Node n = model.food;
        drawNode(g, n);

        updateScore();
    }
//绘制一个结点
    private void drawNode(Graphics g, Node n) {
        g.fillRect(n.x * nodeWidth,
                n.y * nodeHeight,
                nodeWidth - 1,
                nodeHeight - 1);
    }

    public void updateScore() {
        String s = "Score: " + model.score;
        labelScore.setText(s);
    }

    public void update(Observable o, Object arg) {
        repaint();
    }
}