# AFKPlugin
## Description
잠수 시스템 플러그인입니다.

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
/잠수 명령어를 통해 플레이어를 설정한 맵으로 이동하는 기능입니다.  
맵은 /잠수지역설정 명령어를 사용하여 지정할 수 있습니다.

## Commands
```yaml
commands:
  잠수:
    permission: afk.afk
  잠수지역설정:
    permission: afk.setpoint
```
## Permissions
```yaml
permissions:
  afk.afk:
    default: true
  afk.setpoint:
    default: op
```