package cuc.digital.media.algorith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cuc.digital.bean.Record;
import cuc.digital.bean.RecordMeta;

public class Kmeans {
	private int k;// 聚类中心
	private List<Record> records;// 记录集
	private boolean initial;// 是否是初始化
	private boolean finish;
	private List<Record>[] result;// 返回的结果
	private RecordMeta meta;// 关于记录的元数据信息
	private Cosine cosine = new Cosine();
	Record[] centroid;// 中心点
	public Kmeans() {
		this.initial = false;
		this.finish = false;
		
	}

	public List<Record>[] getResult() {
		return result;
	}

	public RecordMeta getMeta() {
		return meta;
	}

	public void setMeta(RecordMeta meta) {
		this.meta = meta;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	/**
	 * 获取聚类中心，刚开始时随机取的中心点,这个地方可以优化，选择相互比较远的点处理
	 */
	private void getCentroid() {
		boolean finishl = true;// 默认为结束
		if (!initial) {
			centroid = new Record[k];
			Random random = new Random();
			for (int i = 0; i < k; i++) {
				int index = random.nextInt(meta.getRecords());
				centroid[i] = records.get(index);// 随机生成中心点
			}
			initial = true;
			finishl=false;
		} else {

			for (int i = 0; i < k; i++)// k为中心数
			{
				List<Record> list = result[i];
				Iterator<Record> it = list.iterator();
				double[] sum = new double[meta.getDimension()];
				int n = 0;
				while (it.hasNext()) {
					n++;
					Record record = it.next();
					double[] re = record.getVector();
					for (int j = 0; j < meta.getDimension(); j++) {
						sum[j] += re[j];
					}
				}
				for (int j = 0; j < meta.getDimension(); j++) {
					sum[j] = sum[j] / n;
				}
				Record nr = new Record();
				nr.setId(0);
				nr.setVector(sum);
				Record temp = centroid[i];// 把这一个中心取出
				centroid[i] = nr;
				if (finishl&&!isFinish(temp, nr)) {
					finishl = false;
				}
			}

		}
		if (finishl) {
			finish = true;
		} else {
			result = new List[k];// 每一次生成中心后，重新生成空结果集
			for (int i = 0; i < k; i++) {
				result[i] = new ArrayList<Record>();// 分配一个list
			}
			execute();// 继续执行
		}
	}

	private boolean isFinish(Record temp, Record nr) {
		boolean finish = true;
		for (int i = 0; i < meta.getDimension(); i++) {
			if (temp.getVector()[i] != nr.getVector()[i]) {
				finish = false;
				break;
			}
		}
		return finish;
	}

	private void execute() {
		if (!initial)
			getCentroid();// 首次生成聚类中心
		for (int i = 0; i < meta.getRecords(); i++) {
			Record record = records.get(i);
			double temp = 0;
			int flag = 0;
			for (int j = 0; j < k; j++) {
				double res = cosine.getCosine(centroid[j].getVector(),
						record.getVector());
				if (res > temp) {
					temp = res;
					flag = j;
				}
			}
			result[flag].add(record);
		}
		getCentroid();
	}

	public void doRun() {
		execute();
	}
}