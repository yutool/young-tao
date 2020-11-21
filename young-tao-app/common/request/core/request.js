import { mergeConfig, dispatchRequest, jsonpRequest} from "./utils.js";
export default class request {
	constructor(options) {
		// 请求公共地址
		this.baseUrl = options.baseUrl || "";
		// 公共文件上传请求地址
		this.fileUrl = options.fileUrl || "";
		// 超时时间
		this.timeout = options.timeout || 6000;
		// 服务器上传图片默认url
		this.defaultUploadUrl = options.defaultUploadUrl || "";
		// 默认请求头
		this.header = options.header || {};
		// 默认配置
		this.config = options.config || {
			isPrompt: true,
			load: true,
			isFactory: true,
      resend: 0
		};
	}
	//post请求
	post({url = '', data = {}, options = {}}) {
		return this.request({
			url: url,
			method: "POST",
			data: data,
			...options
		});
	}

	//get请求
	get({url = '', data = {}, options = {}}) {
		return this.request({
			url: url,
			method: "GET",
			data: data,
			...options
		});
	}

	//put请求
	put({url = '', data = {}, options = {}}) {
		return this.request({
			url: url,
			method: "PUT",
			data: data,
			...options
		});
	}

	//delete请求
	delete({url = '', data = {}, options = {}}) {
		return this.request({
			url: url,
			method: "DELETE",
			data: data,
			...options
		});
	}
	//jsonp请求(只限于H5使用)
	jsonp({url = '', data = {}, options = {}}) {
		return this.request({
			method: "JSONP",
			data: data,
			url: url,
			...options
		});
	}
	//接口请求方法
	async request(data) {
		// 请求数据
		let requestInfo,
		// 是否运行过请求开始钩子
		runRequestStart = false;
		try {
			if (!data.url) {
				throw { code: -1, message: "Missing the url"}
			}
			// 数据合并
			requestInfo = mergeConfig(this, data);
			// 代表之前运行到这里
			runRequestStart = true;
			//请求前回调
			if (this.requestStart) {
				let requestStart = this.requestStart(requestInfo);
				if (typeof requestStart == "object") {
					let changekeys = ["data", "header", "isPrompt", "load", "isFactory"];
					changekeys.forEach(key => {
						requestInfo[key] = requestStart[key];
					});
				} else {
					throw {
						code: -1,
						message: "请求开始拦截器未通过",
						url: requestInfo.url,
						method: requestInfo.method,
						header: requestInfo.header,
						data: requestInfo.data
					}
				}
			}
			let requestResult = {};
			if(requestInfo.method == "JSONP"){
				requestResult = await jsonpRequest(requestInfo);
			} else {
				requestResult = await dispatchRequest(requestInfo);
			}
			//是否用外部的数据处理方法
			if (requestInfo.isFactory && this.dataFactory) {
				//数据处理
				let result = await this.dataFactory({
					...requestInfo,
					response: requestResult
				});
				return Promise.resolve(result);
			} else {
				return Promise.resolve(requestResult);
			}
		} catch (err){
			this.requestError && this.requestError(err);
			return Promise.reject(err);
		} finally {
			// 如果请求开始未运行到，请求结束也不运行
			if(runRequestStart){
				this.requestEnd && this.requestEnd(requestInfo);
			}
		}
	}
}
