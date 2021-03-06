package RecommendPath;

import Bean.EdgeSql;
import Bean.NodeSql;
import GuideDataStructure.Graph;
import RecommendPath.TestPathGenerate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/22.
 */
public class InitMap {
    public static Graph returnGraph() {
        int count = 0;
        NodeSql nodeSql = null;
        List<NodeSql> nodeSqls = new ArrayList<NodeSql>();
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                nodeSql = new NodeSql();
                nodeSql.setId(count);
                nodeSql.setX(i);
                nodeSql.setY(k);
                count++;
                nodeSqls.add(nodeSql);
                nodeSql = null;
            }
        }

        List<EdgeSql> edgeSqls = new ArrayList<EdgeSql>();
        EdgeSql edgeSql = null;
        count = 0;
        for (int n1 = 0; n1 <= 90; n1 = n1 + 10) {
            int behind = n1;
            for (int n2 = n1 + 1; n2 <= n1 + 9; n2++) {
                edgeSql = new EdgeSql();
                edgeSql.setId(count);
                edgeSql.setNode_id1(behind);
                edgeSql.setNodeid2(n2);
                count++;
                edgeSqls.add(edgeSql);
                edgeSql = null;
                behind++;
            }
        }

        for (int n1 = 0; n1 <= 9; n1++) {
            int behind = n1;
            for (int n2 = n1 + 10; n2 <= 99; n2 = n2 + 10) {
                edgeSql = new EdgeSql();
                edgeSql.setId(count);
                edgeSql.setNode_id1(behind);
                edgeSql.setNodeid2(n2);
                count++;
                edgeSqls.add(edgeSql);
                edgeSql = null;
                behind = behind + 10;
            }

        }

//        for (EdgeSql edgeSql1 :edgeSqls)
//    {
//        System.out.println(edgeSql1.getNode_id1()+"-----"+edgeSql1.getNodeid2());
//    }
//        for (NodeSql nodeSql1 :nodeSqls)
//    {
//        System.out.println(nodeSql1.getId()+":"+nodeSql1.getX()+"--"+nodeSql1.getY());
//    }
        Graph graph = new Graph(edgeSqls, nodeSqls);
        return graph;
    }
    public static Graph returnGraphWP(Map<Integer, Double> list,Map<Integer, Integer> pLocation) {
        int count = 0;
        NodeSql nodeSql = null;
        List<NodeSql> nodeSqls = new ArrayList<NodeSql>();
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                nodeSql = new NodeSql();
                nodeSql.setId(count);
                nodeSql.setX(i);
                nodeSql.setY(k);
                count++;
                nodeSqls.add(nodeSql);
                nodeSql = null;
            }
        }

        List<EdgeSql> edgeSqls = new ArrayList<EdgeSql>();
        EdgeSql edgeSql = null;
        count = 0;
        for (int n1 = 0; n1 <= 90; n1 = n1 + 10) {
            int behind = n1;
            for (int n2 = n1 + 1; n2 <= n1 + 9; n2++) {
                edgeSql = new EdgeSql();
                edgeSql.setId(count);
                edgeSql.setNode_id1(behind);
                edgeSql.setNodeid2(n2);
                count++;
                edgeSqls.add(edgeSql);
                edgeSql = null;
                behind++;
            }
        }
        for (int n1 = 0; n1 <= 9; n1++) {
            int behind = n1;
            for (int n2 = n1 + 10; n2 <= 99; n2 = n2 + 10) {
                edgeSql = new EdgeSql();
                edgeSql.setId(count);
                edgeSql.setNode_id1(behind);
                edgeSql.setNodeid2(n2);
                count++;
                edgeSqls.add(edgeSql);
                edgeSql = null;
                behind = behind + 10;
            }

        }
        Graph graph = new Graph(edgeSqls, nodeSqls, list, pLocation);
        return graph;
    }
    public static Graph returnGraphWP(Map<Integer, Double> list, TestPathGenerate testPathGenerate) {
        int count = 0;
        NodeSql nodeSql = null;
        List<NodeSql> nodeSqls = new ArrayList<NodeSql>();
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                nodeSql = new NodeSql();
                nodeSql.setId(count);
                nodeSql.setX(i);
                nodeSql.setY(k);
                count++;
                nodeSqls.add(nodeSql);
                nodeSql = null;
            }
        }

        List<EdgeSql> edgeSqls = new ArrayList<EdgeSql>();
        EdgeSql edgeSql = null;
        count = 0;
        for (int n1 = 0; n1 <= 90; n1 = n1 + 10) {
            int behind = n1;
            for (int n2 = n1 + 1; n2 <= n1 + 9; n2++) {
                edgeSql = new EdgeSql();
                edgeSql.setId(count);
                edgeSql.setNode_id1(behind);
                edgeSql.setNodeid2(n2);
                count++;
                edgeSqls.add(edgeSql);
                edgeSql = null;
                behind++;
            }
        }
        for (int n1 = 0; n1 <= 9; n1++) {
            int behind = n1;
            for (int n2 = n1 + 10; n2 <= 99; n2 = n2 + 10) {
                edgeSql = new EdgeSql();
                edgeSql.setId(count);
                edgeSql.setNode_id1(behind);
                edgeSql.setNodeid2(n2);
                count++;
                edgeSqls.add(edgeSql);
                edgeSql = null;
                behind = behind + 10;
            }

        }
        Graph graph = new Graph(edgeSqls, nodeSqls, list, testPathGenerate);
        return graph;
    }

}
