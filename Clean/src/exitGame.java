
public class exitGame implements action {

    @Override
    public void go() {
        synchronized (Clean.BufferStrategyMutEx) {
            Clean.isRunning = false;
            if (Clean.fullscreen) {
                Clean.device.setDisplayMode(Clean.dm);
                Clean.device.setFullScreenWindow(null);
                Clean.frame.setVisible(false);
                Clean.frame.dispose();
            }
            System.exit(0);
        }
    }

    @Override
    public void right() {
        return;
    }

    @Override
    public void left() {
        return;
    }

}
