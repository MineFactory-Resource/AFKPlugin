# AFKPlugin
## Description
잠수(AFK)와 관련된 기능을 내포한 플러그인입니다.

### Use Spigot Version:
1.18.2
### Tested Spigot Version:
1.18.2
### Required Plugin:
None
## Install Guide
1. 최신 버전의 플러그인 파일을 다운로드합니다.
2. 다운로드한 *.jar 파일을 플러그인 디렉토리에 저장합니다.
## Feature

### 잠수맵 이동 기능
/잠수, /afk 명령어를 통해 플레이어를 설정한 월드(잠수맵)로 이동하는 기능입니다.  
월드 지정은 /잠수지역설정 명령어를 사용하여 지정할 수 있습니다.

### 잠수포인트 시스템
플레이어가 /잠수지역설정 명령어를 통해 지정한 월드로 이동하면, 
config에 설정된 delay와 period를 기준으로 value의 값만큼 플레이어에게 잠수포인트를 지급합니다.

### 잠수포인트 시스템 관련 명령어
/잠수포인트 확인 
- 자신의 잠수포인트 확인

/잠수포인트 확인 <닉네임>
- <닉네임>의 잠수포인트 확인 (OP)

/잠수포인트 설정 <닉네임> <값> 
- <닉네임>의 잠수포인트를 <값>으로 설정 (OP)

/잠수포인트 지급 <닉네임> <값> 
- <닉네임>의 잠수포인트를 <값>만큼 증가 (OP)

/잠수포인트 차감 <닉네임> <값> 
- <닉네임>의 잠수포인트를 <값>만큼 감소 (OP)

### 플러그인 리로드 기능
/afkplugin reload 명령어를 사용하여 플러그인을 리로드할 수 있습니다.

## Commands
```yaml
commands:
  잠수:
    permission: afk.afk
    aliases: [afk]
  잠수지역설정:
    permission: afk.setpoint
  afkplugin:
    permission: afk.reload
  잠수포인트:
    permission: afk.afk
```
## Permissions
```yaml
permissions:
  afk.afk:
    default: true
  afk.setpoint:
    default: op
  afk.reload:
    default: op
```