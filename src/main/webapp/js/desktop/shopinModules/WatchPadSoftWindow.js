 //author by qutengfei for 查看pad安装列表 in 20150723 
/**
* 查看pad安装列表
* for bug
* feature https://tower.im/s/9cCdh
* author qutengfei
*/
Ext.define('ShopinDesktop.WatchPadSoftWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	dataStore : null,
	sid :null,
	padNo :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initCmps(me);
//        sid = config.sid;
//        padNo = config.padNo;
		macAddress=config.macAddress;
		shopId=config.shopId;
		this.superclass.constructor.call(this, {
			title :'查看pad安装列表',
			id:'watchPadSoft',
			height : 350,
			width  : 480,
			constrain : true,
			plain : true, 
			layout:"fit",
			modal: true,
			maximizable:true,
			items :  [ me.gridPanel],
			buttons :[
				{
					text : '关闭',
					handler : function() {
						me.close();
					}
				}
			]
		});
	},
	initCmps : function(me) {
		
//		this.sm = Ext.create('Ext.selection.CheckboxModel',{
//			singleSelect : true
//			});
//		var checkbox = this.sm;
		
		var isok=true;
		this.store = Ext.create('Ext.data.JsonStore', {
			autoLoad : true,
			clearOnPageLoad : true,
			fields : [
				"appName","appPackage","flag","guideId","id"
			],
			proxy : {
					type : 'ajax',
					api : {
						read : _appctx + '/padSupply/watchPadSoft?macAddress='+macAddress+'&shopId='+shopId,
					},
					idParam : 'sid',
					reader : {
						type: "json",
						root : "data"
					}
				}
		});
       
	    this.gridPanel = Ext.create('Ext.grid.Panel', {
	    		id : 'watchPadSoftId',
				autoScroll:true,
				autoWidth:true,
				manageHeight:true,
				columns:[
				    new Ext.grid.RowNumberer(),
					{header:'软件名称',dataIndex:'appName',width:150,sortable:true},
					{header:'安装目录',dataIndex:'appPackage',width:150,sortable:true},
					{header:'当前状态',dataIndex:'flag',width:150,sortable:true,
						renderer:function(value){
								if(value == 0) {
									return '已卸载';
								}
								if(value == 1) {
									return '安装';
								}
						}
					},
//					{header:'guideId',dataIndex:'guideId',width:80,sortable:true},
//					{header:'id',dataIndex:'id',width:80,sortable:true},
				],
				store:this.store,  
				columnLines : true,
//				selModel: this.sm,
				sortableColumns : false
				});
	    
	    me.gridPanel.render(document.body);
		window.onresize=function(){
		        me.gridPanel.setWidth(document.documentElement.clientWidth);
		        me.gridPanel.setHeight(document.documentElement.clientHeight-40);
		    };
		}
});
