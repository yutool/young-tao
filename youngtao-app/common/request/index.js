import request from "./core/request.js";			// 纯粹的数据请求
// import request from "./upload/upload.js";	// 数据请求同时继承了文件上传（包括七牛云上传）
import store from '@/store'

// const baseUrl = "https://youngtao.test.utools.club";
const baseUrl = 'http://localhost:19000';

// 可以new多个request来支持多个域名请求
const $http = new request({
	baseUrl: baseUrl,							//接口请求地址
	fileUrl: baseUrl,							// 服务器本地上传文件地址
	defaultUploadUrl: "api/xxx",	// 服务器上传图片默认url
	//设置请求头（如果使用报错跨域问题，可能是content-type请求类型和后台那边设置的不一致）
	header: {						
		'content-type': 'application/json;charset=UTF-8'
	},
	timeout: 6000,			// 请求超时时间（默认6000）
	// 默认配置（可不写）
	config: {
		isPrompt: true,		// 是否自动提示错误
		load: false,			// 是否显示加载动画
		isFactory: true		// 是否使用数据工厂
	}
});

// 当前接口请求数
let requestNum = 0;
// 请求开始拦截器
$http.requestStart = function(options) {
	store.dispatch('app/setLoading', true)
	if (options.load) {
		if (requestNum <= 0) {
			uni.showLoading({ title: '加载中', mask: true });
		}
		requestNum++;
	}
	// 图片上传大小限制
	if (options.method == "FILE" && options.maxSize) {
		// 文件最大字节: options.maxSize 可以在调用方法的时候加入参数
		const maxSize = options.maxSize;
		for (let item of options.files) {
			if (item.size > maxSize) {
				setTimeout(() => {
					uni.showToast({ title: "图片过大，请重新上传", icon: "none" });
				}, 500);
				return false;
			}
		}
	}
	options.header['Authorization'] = "Bearer xx";
	return options; // return false 表示请求拦截，不会继续请求
}

// 请求结束
$http.requestEnd = function(options) {
	store.dispatch('app/setLoading', false)
	// 判断当前接口是否需要加载动画
	if (options.load) {
		requestNum --;
		if (requestNum <= 0) {
			uni.hideLoading();
		}
	}
}

// 错误回调
$http.requestError = function (e) {
	// e.statusCode === 0 是参数效验错误抛出的
	if (e.statusCode === 0) {
		throw e;
	} else {
		uni.showToast({ title: "网络错误，请检查一下网络", icon: "none" });
	}
}

let loginPopupNum = 0;
// 所有接口数据处理（可在接口里设置不调用此方法）
$http.dataFactory = async function(res) {
	console.log("http response: ", {
		url: res.url,
		body: res.data,
		method: res.method,
		header: res.header,
		response: res.response,
	});
	// 请求成功
	if (res.response.statusCode && res.response.statusCode == 200) {
		const result = res.response.data;
		if (typeof (result) == "string") {
			result = JSON.parse(result);
		}
		
		// 判断数据是否请求成功
		if (result.success || result.code == 0) {
			return Promise.resolve(result);
		} 
		// 未登录
		else if (result.code == "1000" || result.code == "1001" || result.code == 1100) {
			let content = '此时此刻需要您登录喔~';
			if (loginPopupNum <= 0) {
				loginPopupNum++;
				uni.showModal({
					title: '温馨提示',
					content: content,
					confirmText: "去登录",
					cancelText: "再逛会",
					success: function (res) {
						loginPopupNum--;
						if (res.confirm) {
							uni.navigateTo({
								url: "/pages/user/login"
							});
						}
					}
				});
			}
			// 返回错误的结果(catch接受数据)
			return Promise.reject({
				resultCode: result.code,
				message: result.message
			});
		} 
		// 其他错误
		else {
			if (res.isPrompt) {
				uni.showToast({
					title: result.message,
					icon: "none",
					duration: 2000
				});
			}
			return Promise.reject({
				resultCode: result.code,
				message: result.message
			});
		}
	} 
	// 请求失败
	else {
		return Promise.reject(
			`${res.method} ${res.url} ${res.response.statusCode} (${res.response.data.error}), exception: ${res.response.data.message}`
		);
	}
};

// 添加获取七牛云token的方法
$http.getQnToken = function(callback){
	//该地址需要开发者自行配置（每个后台的接口风格都不一样）
	$http.get("api/kemean/aid/qn_upload").then(data => {
		/*
		 *接口返回参数：
		 *visitPrefix:访问文件的域名
		 *token:七牛云上传token
		 *folderPath:上传的文件夹
		 *region: 地区 默认为：SCN
		 */
		callback({
			visitPrefix: data.visitPrefix,
			token: data.token,
			folderPath: data.folderPath,
			region: "SCN"
		});
	});
}

export default $http;
