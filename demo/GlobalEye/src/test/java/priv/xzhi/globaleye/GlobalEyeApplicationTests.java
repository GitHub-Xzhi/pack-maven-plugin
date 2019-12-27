package priv.xzhi.globaleye;

import priv.xzhi.globaleye.bean.TSrcAlarm;
import priv.xzhi.globaleye.dao.TSrcAlarmMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlobalEyeApplicationTests {

	@Autowired
	private TSrcAlarmMapper tSrcAlarmMapper;

	@Test
	public void t1() {
		List<TSrcAlarm> tSrcAlarms = tSrcAlarmMapper.selectList(null);
		System.out.println(tSrcAlarms);
		System.out.println(">>>"+tSrcAlarms.size());
	}

}
