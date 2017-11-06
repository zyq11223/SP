package RecommendPath;

import DataStructure.Graph;
import DataStructure.Node;
import DataStructure.Path;
import MainCode.Guider;
import TestCode.InitMap;

import java.util.*;

/**
 * Created by Administrator on 2017/6/28 0028.
 */
public class TestPathGenerate {
    //N types of product eg:P1,P2,P3...Pn;表明有多少种商品
    final static int N = 160;
    //购买的 最大上限
    public static final int Npi = N / 4;
    //K types of customer eg:C1,C2,C3...Cn;表明有多少类型的顾客
    final static int K = 10;
    //M paths 表示要运行多少次，产生多少路径
    final static int M = 6000;
    //每个点有多少种类的商品。
    final static int NN = 10;
    //给定一个概率分布，这里的概率意思是每顾客对每一种商品的喜好程度，概率和为1。
    public static Map<Integer, double[]> CustomersProducts;
    //所有用户簇类平均的一个概率。
// public static double[] MeanCustomersProducts = new double[N];
    //T个新的顾客，只知道他们将要购物的清单列表
    final int T = 10;
    //所有的购物列表集合
    public ArrayList<ArrayList<Integer>> shopLists;
    public ArrayList<ArrayList<Integer>> getShopLists() {
        return shopLists;
    }

    /**
     * Key 为x坐标，Value 为y坐标
     */
    public static Map<Integer, Integer> pLocation;
    private static HashMap<Integer, Double> allProducts;
    public static Map<Integer, Set<Integer>> shelf;
    /**
     * String 存放的是路径，Set 存放的是购买清单
     */
    public static Map<String, Set<Integer>> history;
    /**
     * Integer 为顾客标记，Set 存放的是购买清单
     */
    public static Map<Integer, Set<Integer>> TNewCustomer;

    /**
     * Init running
     */
    public TestPathGenerate(boolean printOrNot) {
        Random random = new Random();
        history = new HashMap<String, Set<Integer>>();
        TNewCustomer = new HashMap<Integer, Set<Integer>>();
        //初始化所有商品 所有概率为0
        InitProducts();
        //初始化，不同簇类的用户对喜欢不同类型的产品喜好不一样
        InitCustomersProducts();
        //平均用户簇类
//        MeanCustomerCluster();
        //所有商品随机分配给16个点 0-15
        FillShelf();

        //***************Generate M Path********************
        shopLists = new ArrayList<ArrayList<Integer>>();
        for (int L = 0; L < M; L++) {
            //choose type of customer;
            int i = random.nextInt(K);
            //choose nb of products that Ci will buy;
            int nb = random.nextInt(Npi);
//            choose which products are bought;
            ArrayList<Integer> shopList = new ArrayList<>();
            //轮盘选取 product 下面选取商品的方法导致概率低的商品永远不会被购买
//            double meanPro = 1.0 / N;
            for (int k = 0; k < nb; k++) {
                double[] productProbability = CustomersProducts.get(i);
//                //if productProbability higher than meanPro(1+0.8),i can consider the customer will bought it
//                int productId = random.nextInt(N);
//                while (meanPro *(1+0.7) > productProbability[productId]) {
//                    productId = random.nextInt(N);
//                }
                //大转盘 概率q q在哪个区间选取哪个商品
                double q = Math.random();
                //创建转盘
                double[] wheel = new double[N];
                wheel[0] = productProbability[0];
                for (int j = 1; j < N; j++) {
                    wheel[j] = productProbability[j] +wheel[j-1];
                }
                int productId = 0;
                //
                for (int j = N-1; j >= 1 ; j--) {

                    if (wheel[j] > q && wheel[j-1]>0)
                    {
                         productId = j;
                    }

                }
                shopList.add(productId);
            }
            if (printOrNot) {
                System.out.println();
                System.out.println("历史客户" + L + "输出待购买商品列表:");
                for (Integer toBuy : shopList) {
                    System.out.print(toBuy + " ");
                }
            }
            shopLists.add(shopList);
//            System.out.println();
            GetPath(shopList, false);
        }
        //***************Generate M Path********************

        //***************Generate T Customer********************
        for (int L = 0; L < T; L++) {
            //choose type of customer;
            int i = random.nextInt(K);
            //choose nb of products that Ci will buy;
            int nb = random.nextInt(N / 4);
            //choose which products are bought;
            Set<Integer> shopList = new HashSet<Integer>();
            for (int k = 0; k < nb; k++) {
                double[] productProbability = CustomersProducts.get(i);
                double meanPro = 1.0 / N;
                int productId = random.nextInt(N);
                while (meanPro * (1 + 0.6) > productProbability[productId]) {
                    productId = random.nextInt(N);
                }
                shopList.add(productId);
            }
            if (printOrNot) {
                System.out.println();
                System.out.println("新客户" + L + "待购买商品列表:");
                for (Integer toBuy : shopList) {
                    System.out.print(toBuy + " ");
                }
            }
            TNewCustomer.put(L, shopList);
        }
    }
   //平均簇类只有一个，这里的平均不应该是对顾客簇类的平均，而是购买的平均，所以下面是错误的，应该是对购买历史的平均得到的平均用户簇类，
    //所以这里的平均客户簇类，是对历史数据进行聚类后再平均得到的平均
//    private void MeanCustomerCluster() {
//        for (Map.Entry<Integer, double[]> e : CustomersProducts.entrySet()) {
//            double[] p = e.getValue();
//            for (int i = 0; i < p.length; i++) {
//                MeanCustomersProducts[i] += p[i];
//            }
//        }
//        for (int i = 0; i < MeanCustomersProducts.length; i++) {
//            MeanCustomersProducts[i] = MeanCustomersProducts[i] / CustomersProducts.size();
//        }
////          平均所有用户的喜好度 得到平均用户簇类 ／输出这个簇类对每一个产品的喜好程度。
////        for (Double d:MeanCustomersProducts) {
////            System.out.println(d);
////        }
//    }

    /**
     * @param shopList1  购买列表
     * @param printOrNot 是否打印获取过程
     */
    private void GetPath(ArrayList<Integer> shopList1, Boolean printOrNot) {
        Stack<Integer> shopListTemp = new Stack<>();
        Stack<Integer> shopList = new Stack<>();
        for (Integer i : shopList1) {
            shopListTemp.push(i);
        }
        for (Integer i : shopListTemp) {
            shopList.push(i);
        }
        Set<Integer> shopListSet = new HashSet<Integer>();
        for (Integer i : shopList) {
            shopListSet.add(i);
        }
        //Get product location
        if (printOrNot) {
            System.out.println();
            System.out.println("输出待购买商品位置:");
            for (Integer i : shopList)
                System.out.println("商品" + i + "的位置" + "->" + pLocation.get(i));
            System.out.println();
        }
        //Generating paths;
        // 初始化graph
        Graph graph = InitMap.returnGraph();
        Set<Node> nodes = new HashSet<Node>();
        for (int i : shopList)
            nodes.add(graph.getNode(pLocation.get(i)));
        if (printOrNot) {
            for (Node node : nodes) {
                System.out.print(node.N + "  ");
            }
            System.out.println("  ");
        }
        Stack<Node> finalPath = new Stack<Node>();
        Node lastNode = graph.getNode(0);

        while (shopList.size() != 0 && shopList != null) {
            Node des = graph.getNode(pLocation.get(shopList.pop()));
            List<Path> paths = Guider.getSingleDestPath(graph, lastNode, des, null, 0.1, false);
            //出现了起始点等于终点的情况 此时去除购买列表中所有位于起始点中的商品 eg:  4->4 路径不存在
            if (paths.isEmpty()) {
                nodes.remove(graph.getNode(des.N));
                Set<Integer> products = shelf.get(des.N);
                for (Integer product : products) {
                    if (shopList.contains(product))
                        while (shopList.contains(product))
                            shopList.remove(product);
                    if (printOrNot) {
                        System.out.println("清除重复点中商品：" + product + "  位于" + des.N);
                    }
                }
                if (printOrNot) {
                    System.out.println();
                    System.out.println("清除重复点中");
                    for (Integer i : shopList) {
                        System.out.println("商品" + i + "的位置" + "->" + pLocation.get(i));
                    }
                }
                continue;
            }
            Path bestPath = paths.get(0);
            Stack<Node> tempPath = new Stack<Node>();
            for (Node node : bestPath.getNodes()) {
                tempPath.push(node);
                Set<Integer> products = shelf.get(node.N);
                for (Integer product : products) {
                    if (shopList.contains(product)) {
                        while (shopList.contains(product))
                            shopList.remove(product);
                        if (printOrNot) {
                            System.out.println("清除路径上商品ID：" + product + "  位于" + node.N);
                        }

                    }
                }
                nodes.remove(graph.getNode(node.N));
            }
            if (printOrNot) {
                System.out.println();
                System.out.println("清除路径上已存在点：");
                for (Integer i : shopList) {
                    System.out.println("商品" + i + "的位置" + "->" + pLocation.get(i));
                }
                for (Node node : nodes) {
                    System.out.println("位置" + node.N);
                }
                if (shopList.isEmpty()) {
                    System.out.println("购物列表shopList已完成清空操作");
                }
            }
            while (!tempPath.isEmpty()) {
                if (!finalPath.isEmpty()) {
                    Node topNode = finalPath.peek();
                    if (topNode == tempPath.peek()) {
                        finalPath.pop();
                    }
                }
                finalPath.push(tempPath.pop());
            }
            if (shopList.isEmpty()) {
                break;
            }
            lastNode = graph.getNode(finalPath.peek().N);
        }

        //使用StringBuffer构造路径
        StringBuffer stringBuffer = new StringBuffer();
        for (Node node : finalPath) {
            if (printOrNot) {
                System.out.print(node.N + "->");
            }
            stringBuffer.append(node.N);
            stringBuffer.append(",");
        }
        // 这里添加判断，过短的路径要剔除
        if (stringBuffer.toString().length() >  20)
        history.put(stringBuffer.toString(), shopListSet);
    }

    private void InitCustomersProducts() {
        CustomersProducts = new HashMap<Integer, double[]>();
        for (int i = 0; i < K; i++) {
            double[] products = getRandDistArray(N, 1.0);
            CustomersProducts.put(new Integer(i), products);
        }
//        // 检查初始化是否和为1
//        for (HashMap.Entry<Integer, double[]> temp:CustomersProducts.entrySet()) {
//            double sum = 0;
//            for (Double d:temp.getValue())
//            {
//                sum += d;
//            }
//            System.out.println("顾客："+temp.getKey()+" 概率和："+sum);
//        }
//
    }

    private static void FillShelf() {
        shelf = new HashMap<Integer, Set<Integer>>();
        pLocation = new HashMap<Integer, Integer>();
        for (int i = 0; i < 16; i++) {
            Set<Integer> products = new HashSet<Integer>();
            for (int j = 0; j < NN; j++) {
                while (true) {
                    Random random = new Random();
                    int productId = random.nextInt(N);
                    if (allProducts.containsKey(productId)) {
                        products.add(productId);
                        pLocation.put(productId, i);
                        allProducts.remove(productId);
                        break;
                    } else {
                        continue;
                    }
                }
            }
            shelf.put(i, products);
        }
        System.out.println();
        System.out.println("输出货架上的商品");
        for (Map.Entry e : shelf.entrySet()) {
            System.out.print(e.getKey());
            System.out.println(e.getValue());
        }
    }

    private static void InitProducts() {
        allProducts = new HashMap<Integer, Double>();
        for (int i = 0; i < N; i++) {
            allProducts.put(new Integer(i), 0.0);
        }
    }

    public static double[] getRandDistArray(int n, double m) {
        double randArray[] = new double[n];
        double sum = 0;

        // Generate n random numbers
        for (int i = 0; i < randArray.length; i++) {
            randArray[i] = Math.random();
            sum += randArray[i];
        }

        // Normalize sum to m
        for (int i = 0; i < randArray.length; i++) {
            randArray[i] /= sum;
            randArray[i] *= m;
        }
        return randArray;
    }
}
