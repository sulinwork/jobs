package bean;

/**
 * @author sulin
 * @create 2019-10-13 16:20
 */
public class Jobs {

    private String jobName; //岗位

    private String jobUrl;//访问Url

    private String companyName;//公司名

    //企业类型
    private String companyType;
    //企业人数
    private String companySize;
    //企业领域
    private String companyField;

    private String workAddr;//公司地址


    private String salary;//薪水

    private String pushDate;//发布日期

    private String msgType;
    //工作年限要求
    private String needWorkDate;
    //学历要求
    private String record;
    //需求人数
    private String needNum;
    //任职要求
    private String requirements;


    public Jobs() {

    }

    public Jobs(String jobName, String jobUrl, String companyName, String workAddr, String salary, String pushDate) {
        this.jobName = jobName;
        this.jobUrl = jobUrl;
        this.companyName = companyName;
        this.workAddr = workAddr;
        this.salary = salary;
        this.pushDate = pushDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkAddr() {
        return workAddr;
    }

    public void setWorkAddr(String workAddr) {
        this.workAddr = workAddr;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public String getNeedWorkDate() {
        return needWorkDate;
    }

    public void setNeedWorkDate(String needWorkDate) {
        this.needWorkDate = needWorkDate;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getNeedNum() {
        return needNum;
    }

    public void setNeedNum(String needNum) {
        this.needNum = needNum;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyField() {
        return companyField;
    }

    public void setCompanyField(String companyField) {
        this.companyField = companyField;
    }

    @Override
    public String toString() {
        return jobName + "," + companyName + "," + companyType + "," + companySize + "," + companyField + "," + workAddr + "," + salary + "," + msgType + "," + pushDate + "," + requirements +
                "," + jobUrl;
    }
}
