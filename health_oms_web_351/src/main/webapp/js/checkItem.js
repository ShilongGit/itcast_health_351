var vue = new Vue({
    el: '#app',
    data:{
        pagination: {//分页相关模型数据
            currentPage: 1,//当前页码
            pageSize:10,//每页显示的记录数
            total:0,//总记录数
            queryString:null//查询条件
        },
        dataList: [],//当前页要展示的分页列表数据
        formData: {},//表单数据
        dialogFormVisible: false,//增加表单是否可见
        dialogFormVisible4Edit:false,//编辑表单是否可见
        rules: {//校验规则
            code: [{ required: true, message: '项目编码为必填项', trigger: 'blur' }],
            name: [{ required: true, message: '项目名称为必填项', trigger: 'blur' }]
        },
        backendUrl:"http://localhost:82"
    },
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.findPage();
    },
    methods: {
        //编辑
        //1. 数据校验，如果校验成功，继续，如果失败，提示信息
        //2. 发送请求，编写后端
        //3. 成功回调后：关闭弹框，重新加载，重置表单
        handleEdit() {
            this.$refs['dataEditForm'].validate((valid)=>{
                if(valid){
                    axios.defaults.withCredentials = true;
                    axios.post(this.backendUrl + "/checkitem/edit.do", this.formData).then((response)=>{
                        if(response.data.flag){
                            this.$message({
                                message: response.data.message,
                                type: "success"
                            });
                        }else{
                            this.$message({
                                message: response.data.message,
                                type: "error"
                            });
                        }
                        this.dialogFormVisible4Edit = false;
                        this.findPage();
                        this.resetForm();
                    }).catch((err)=>{
                        this.showMessage(err);
                    });
                }
            });


        },
        //添加
        //1. 填充数据，数据校验
        //2. 如果校验成功，发送请求到后端;如果失败，提示错误信息
        //3. 返回结果，如果失败，提示失败，如果成功，提示成功
        //4. 关闭弹框
        //5. 重新查询
        //6. 重置表单
        handleAdd (){
            //1. 填充数据，数据校验
            this.$refs['dataAddForm'].validate((valid)=>{
                //2. 如果校验成功，发送请求到后端;如果失败，提示错误信息
                //valid 校验结果
                if(valid){
                    //校验成功
                    //发送请求
                    axios.defaults.withCredentials = true;
                    axios.post(this.backendUrl + "/checkitem/add.do", this.formData).then((response)=>{
                        console.log(response);
                        //3. 返回结果，如果失败，提示失败，如果成功，提示成功
                        if(response.data.flag){
                            this.$message({
                                message: response.data.message,
                                type: "success" // info ,error, success, warning
                            });
                        }else{
                            this.$message({
                                message: response.data.message,
                                type: "error" // info ,error, success, warning
                            });
                        }
                        //4. 关闭弹框
                        this.dialogFormVisible = false;
                        //5. 重新查询
                        this.findPage();
                        //6. 重置表单
                        this.resetForm();
                    }).catch((err)=>{
                        this.showMessage(err);
                    });
                }
            });
        },
        _findPage(){
            if(this.pagination.queryString!= null && this.pagination.queryString.length > 0){
                //如果条件不为null，则查询第一页
                this.pagination.currentPage = 1;
            }
            this.findPage();
        }
        ,
        //1. 分装分页参数
        //2. 发送请求，查询数据
        //3. 列表数据需要在表格中展示， 分页组件实现
        //分页查询
        findPage() {
            //1. 分装分页参数
            var param = {
                currentPage: this.pagination.currentPage,
                pageSize: this.pagination.pageSize,
                queryString: this.pagination.queryString
            };
            //2. 发送请求，查询数据
            axios.defaults.withCredentials = true;
            axios.post(this.backendUrl+ "/checkitem/findPage.do", param).then((response)=>{
                //3. 列表数据需要在表格中展示， 分页组件实现
                this.dataList = response.data.rows;
                this.pagination.total = response.data.total;
            }).catch((err)=>{
                this.showMessage(err);
            });

        },
        // 重置表单
        resetForm() {
            //对表单数据清空;
            this.formData = {};
            //对整个表单进行重置，移除校验结果
            if(this.$refs['dataAddForm'] != null){
                this.$refs['dataAddForm'].resetFields();
            }
        },
        // 操作新建
        handleCreate() {
            //1. 重置表单
            this.resetForm();
            //2. 弹出窗口
            this.dialogFormVisible = true;
        },
        //1. 弹出窗口
        //2. 重置表单
        //3. 根据id回显数据
        handleUpdate(row) {
            //1. 弹出窗口
            this.dialogFormVisible4Edit = true;
            //2. 重置表单
            this.resetForm();
            //3. 根据id查询，回显数据
            axios.defaults.withCredentials = true;
            axios.get(this.backendUrl+"/checkitem/findById.do?id="+row.id).then((respose)=>{
                console.log(respose);
                ///查询成功后，回显
                this.formData = respose.data.data;
            }).catch((err)=>{
                this.showMessage(err);
            });
        },
        //切换页码
        handleCurrentChange(currentPage) {
            this.pagination.currentPage = currentPage;
            this.findPage();
        },
        // 删除
        handleDelete(row) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //点击确认按钮要执行的操作： 删除操作
                axios.defaults.withCredentials = true;
                axios.get(this.backendUrl+"/checkitem/delById.do?id="+row.id).then((response)=>{
                    if(response.data.flag){
                        this.$message({
                            type: 'success',
                            message: response.data.message
                        });
                    }else{
                        this.$message({
                            type: 'error',
                            message:response.data.message
                        });
                    }
                    //重新查询
                    this.findPage();
                }).catch((err)=>{
                    this.showMessage(err);
                });

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        //权限不足提示
        showMessage(r){
            console.log(r);
            if (r.message.indexOf("403") != -1){
                this.$message.error("无权访问");
            }else{
                this.$message.error("未知错误");
            }
        }

    }
});