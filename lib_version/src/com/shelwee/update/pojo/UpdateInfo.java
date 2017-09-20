package com.shelwee.update.pojo;


/**
 * Created by ShelWee on 14-5-8.
 */
public class UpdateInfo {
	private int status;
    private String appName;
    private String packageName;
    private String versionCode;
    private String versionName;
    private String apkUrl;
    private String changeLog;
    private String updateTips;
    
    private String created_at;
    private String size;
    private int isForce;
    private int isAutoInstall;

    public int getIsAutoInstall() {
        return isAutoInstall;
    }

    public void setIsAutoInstall(int isAutoInstall) {
        this.isAutoInstall = isAutoInstall;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getIsForce() {
		return isForce;
	}

	public void setIsForce(int isForce) {
		this.isForce = isForce;
	}

	public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }
}
