//
//  RootViewController.m
//  Base
//
//  Created by ns on 2020/5/14.
//

#import "RootViewController.h"

@interface RootViewController ()

@end

@implementation RootViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}
-(void)viewWillAppear:(BOOL)animated{
  [super viewWillAppear:animated];
  self.navigationController.navigationBar.hidden = YES;
}
- (void)viewWillDisappear:(BOOL)animated{
  [super viewWillDisappear:animated];
}

@end
