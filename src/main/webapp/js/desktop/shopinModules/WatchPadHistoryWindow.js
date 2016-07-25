/**
* 查看pad操作历史记录
* author syk
*/
Ext.define('ShopinDesktop.WatchPadHistoryWindow', {
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
        padNo = config.padNo;
		this.superclass.constructor.call(this, {
			title :'查看pad历史记录',
			id:'watchPadHistory',
			height : 500,
			width  : 1200,
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
				"operatorSid","operator","operatTime","description"
			],
			proxy : {
					type : 'ajax',
					api : {
						read : _appctx + '/padBaseinfo/selectPadOperateLogByPadNo?padNo='+padNo,
					},
					idParam : 'sid',
					reader : {
						type: "json",
						root : "list"
					}
				}
		});
       
	    this.gridPanel = Ext.create('Ext.grid.Panel', {
	    		id : 'watchPadHistoryId',
				autoScroll:true,
				autoWidth:true,
				manageHeight:true,
				columns: {
				    items: [
				            new Ext.grid.RowNumberer(),
						    {header:'操作时间',dataIndex:'operatTime',width:120,sortable:true},
							{header:'操作人ID',dataIndex:'operatorSid',width:50,sortable:true},
							{header:'操作人',dataIndex:'operator',width:80,sortable:true},
							{header:'操作描述',dataIndex:'description',width:900,sortable:true/*, renderer: function(value, meta, record){me.formatDesc(value, meta, record)}*/}
							]
				    /*defaults: {
				        css : 'height:27px; vertical-align:middle; font-size:12;'
				    }*/
				},
				store:this.store,  
				columnLines : true,
//				selModel: this.sm,
				sortableColumns : false,
				listeners:{}
				});
	    
	    me.gridPanel.render(document.body);
	    
	    
	    //
	    var view = this.gridPanel.getView();
	    var tip = Ext.create('Ext.tip.ToolTip', {
	    	maxWidth:700,
	        // The overall target element.
	        target: view.el,
	        dismissDelay:0 , //不隐藏提示：默认5500ms
	        // Each grid row causes its own separate show and hide.
	        delegate: view.itemSelector,
	        // Moving within the row should not hide the tip.
	        trackMouse: true,
	        // Render immediately so that tip.body can be referenced prior to the first show.
	        renderTo: Ext.getBody(),
	        listeners: {
	            // Change content dynamically depending on which element triggered the show.
	            beforeshow: function updateTipBody(tip) {
	                tip.update('该单元格内容为："' + view.getRecord(tip.triggerElement).get('description') + '"');
	            }
	        }
	    });
	    //
		window.onresize=function(){
		        me.gridPanel.setWidth(document.documentElement.clientWidth);
		        me.gridPanel.setHeight(document.documentElement.clientHeight-40);
		    };
		},
	
	formatDesc:function(value, meta, record) {
	    var max = 15;  //显示多少个字符

	    meta.tdAttr = 'data-qtip="' + value + '"';

	    return value.length < max ? value :value.substring(0, max - 3) + '...';

	}
});
