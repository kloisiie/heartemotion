from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import collections

app = FastAPI()
#心率数据缓存
HRCache = collections.OrderedDict()

class HeartRate(BaseModel):
	taskId: str
	time: int
	hr: int

class RunResult():
	# -1	执行失败		算法执行中的未知错误
	# 0	    无情绪	    
	# 1	    平稳	
	# 2	    烦躁	
	# 3	    高兴	
	status = -1
	# 应对手段
	means = ''
	# 失败信息
	errMsg = ''

@app.post('/arithmetic/execute')
def execute(heartRates: List[HeartRate]):
	results = []
	for heartRate in heartRates:
		results.append(handle(heartRate))
	return results
	
def handle(heartRate: HeartRate):
	# 初始化字典
	if HRCache.get(heartRate.taskId) is None:
		HRCache[heartRate.taskId] = collections.OrderedDict()
	
	# 心率数据时间是否连续，如果超过30秒，将所有数据清空
	interval = 0
	for key in reversed(HRCache[heartRate.taskId].keys()) :
		interval = heartRate.time - key
		break
	if interval > 30:
		HRCache[heartRate.taskId].clear()
	print('interval: {}'.format(interval))
	
	# 加入缓存
	HRCache.get(heartRate.taskId)[heartRate.time] = heartRate.hr
	
	# 排序
	_sortHR = collections.OrderedDict()
	for i in sorted (HRCache.get(heartRate.taskId).keys()) : 
		_sortHR[i] = HRCache.get(heartRate.taskId)[i]
	HRCache[heartRate.taskId] = _sortHR
	
	
	# 数据清理， 如果超过100条以后，自动删除第一条
	if len(HRCache[heartRate.taskId]) >= 100 :
		for key in HRCache[heartRate.taskId].keys() :
			HRCache[heartRate.taskId].pop(key)
			break
			
	
	# 执行算法
	return doRun(HRCache[heartRate.taskId])
	

# 心率情绪预测算法实现，HRDict为心率字典
def doRun(HRDict):

	if len(HRDict) <= 5 :
		#数据量不够
		result = RunResult()
		result.status = 0
		result.means = ''
		result.errMsg = ''
		return result
	
	sum = 0
	for key, value in HRDict.items():
		sum += value

	avg = sum / len(HRDict)
	print('avg: {}'.format(avg))
	
	if avg <= 85 :
		result = RunResult()
		result.status = 1
		result.means = '多走动'
		result.errMsg = ''
		return result
		
	if avg <= 98 :
		result = RunResult()
		result.status = 3
		result.means = '多睡觉'
		result.errMsg = ''
		return result
		
	if avg > 98 :
		result = RunResult()
		result.status = 2
		result.means = '多喝热水'
		result.errMsg = ''
		return result
	